package br.edu.ifrs.controller;


import br.edu.ifrs.connectionFactory.ConnectionBD;
import br.edu.ifrs.form.JogoDTO;
import br.edu.ifrs.form.JogoForm;
import br.edu.ifrs.form.PlataformaDTO;
import br.edu.ifrs.model.Jogador;
import br.edu.ifrs.model.Jogo;
import br.edu.ifrs.model.Plataforma;
import br.edu.ifrs.persistence.JogoDao;
import br.edu.ifrs.persistence.PlataformaDao;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class JogoController {

    private final JogoDao jdao = new JogoDao(ConnectionBD.connection("prodUnit"));
    private final PlataformaDao pdao = new PlataformaDao(ConnectionBD.connection("prodUnit"));

    @GetMapping("/jogo")
    public String listarJogos(@RequestParam(defaultValue = "0") int offset,
                              @RequestParam(defaultValue = "10") int limit,
                              Model model) {
        List<Jogo> jogos = jdao.findAll(offset, limit);
        model.addAttribute("jogos", jogos);
        model.addAttribute("offset", offset);
        model.addAttribute("limit", limit);
        return "jogo";
    }

    @PostMapping("/jogo/salvar")
    public String salvarOuEditarJogo(@ModelAttribute JogoForm form,
                                     RedirectAttributes redirectAttributes,
                                     Model model) {
        try {
            int ano = Integer.parseInt(form.getAnoLancamento());
            Jogo jogo;
            if (form.getId() != null && form.getId() > 0) {
                jogo = jdao.find(form.getId());
                jogo.setTitulo(form.getTitulo());
                jogo.setAnoLancamento(ano);
                jogo.setDesenvolvedora(form.getDesenvolvedora());
                jogo.setPublicadora(form.getPublicadora());
                jdao.update(jogo);
                redirectAttributes.addFlashAttribute("mensagem", "Jogo atualizado com sucesso!");
            } else {
                jogo = new Jogo(form.getTitulo(), ano, form.getDesenvolvedora(), form.getPublicadora());
                jdao.insert(jogo);
                redirectAttributes.addFlashAttribute("mensagem", "Jogo cadastrado com sucesso!");
            }

            return "redirect:/jogo";

        } catch (IllegalArgumentException e) {
            model.addAttribute("erroModal", "Erro ao salvar jogo: " + e.getMessage());
            model.addAttribute("abrirModal", true);
            model.addAttribute("form", form);
            return listarJogos(0,10,model);
        }
    }
    @PostMapping("/jogo/deletar/{id}")
    public String deletarJogo(@PathVariable int id, RedirectAttributes redirectAttributes){
        try{
            jdao.delete(id);
            redirectAttributes.addFlashAttribute("mensagem", "Jogador excluído com sucesso!");
        } catch(Exception e){
            redirectAttributes.addFlashAttribute("erro", "Erro ao excluir jogador: " + e.getMessage());
        }
        return "redirect:/jogo";
    }

    @GetMapping("/jogo/{id}/plataformas")
    @ResponseBody
    public List<PlataformaDTO> listarPlataformas(@PathVariable int id) {
        List<Plataforma> plataformas = jdao.find(id).getPlataformas();
        return plataformas.stream()
                .map(p -> new PlataformaDTO(p.getId(), p.getNome(), p.getEmail()))
                .toList();
    }

    @PostMapping("/jogo/incluir-plataforma")
    public String incluirPlataforma(@RequestParam int jogoId, @RequestParam int plataformaId, RedirectAttributes re) {
        Jogo j = jdao.find(jogoId);
        Plataforma p = pdao.find(plataformaId);
        if(p.getJogosDisponiveis().contains(j)){
            re.addFlashAttribute("erro", "Plataforma já consta na lista!");
        }
        else{
            p.getJogosDisponiveis().add(j);
            pdao.update(p);
            re.addFlashAttribute("mensagem", "Plataforma incluída com sucesso!");
        }
        return "redirect:/jogo";
    }
    @DeleteMapping("/jogo/{jogoId}/remover-plataforma/{plataformaId}")
    @ResponseBody
    public ResponseEntity<?> removerPlataforma(@PathVariable int jogoId, @PathVariable int plataformaId) {
        Plataforma p = pdao.find(plataformaId);
        Jogo j = jdao.find(jogoId);
        if (p == null || j == null) {
            return ResponseEntity.notFound().build();
        }
        p.getJogosDisponiveis().remove(j);
        j.getPlataformas().remove(p);
        pdao.update(p);
        return ResponseEntity.ok().build();
    }



}
