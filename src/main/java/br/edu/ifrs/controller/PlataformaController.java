package br.edu.ifrs.controller;


import br.edu.ifrs.connectionFactory.ConnectionBD;
import br.edu.ifrs.form.JogadorDTO;
import br.edu.ifrs.form.PlataformaForm;
import br.edu.ifrs.model.Jogador;
import br.edu.ifrs.model.Plataforma;
import br.edu.ifrs.model.TipoPlataforma;
import br.edu.ifrs.persistence.JogadorDao;
import br.edu.ifrs.persistence.PlataformaDao;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class PlataformaController {
    private final PlataformaDao pdao = new PlataformaDao(ConnectionBD.connection("prodUnit"));
    private final JogadorDao jdao = new JogadorDao(ConnectionBD.connection("prodUnit"));

    @GetMapping("/plataforma")
    public String listarPlataformas(Model model){
        List<Plataforma> plataformas = pdao.findAll(0,10);
        model.addAttribute("plataformas", plataformas);
        return "plataforma";
    }

    @PostMapping("/plataforma/salvar")
    public String salvarOuEditar(@ModelAttribute PlataformaForm form,
                                 RedirectAttributes ra, Model model){
        try{
            TipoPlataforma tipo = TipoPlataforma.valueOf(form.getTipo().toUpperCase());
            Plataforma p;
            if(form.getId() != null && form.getId() > 0){
                p = pdao.find(form.getId());
                p.setTipo(tipo);
                p.setProprietaria(form.getProprietaria());
                p.setEmail(form.getEmail());
                pdao.update(p);
                ra.addFlashAttribute("mensagem", "Plataforma atualizada com sucesso!");
            }
            else{
                p = new Plataforma(form.getNome(), tipo, form.getProprietaria(), form.getEmail());
                pdao.insert(p);
                ra.addFlashAttribute("mensagem", "Plataforma cadastrada com sucesso!");
            }
            return "redirect:/plataforma";
        }catch (IllegalArgumentException e){
            model.addAttribute("erroModal", "Erro ao salvar plataforma: " + e.getMessage());
            model.addAttribute("abrirModal", true);
            model.addAttribute("form", form);
            return listarPlataformas(model);
        }
    }

    @PostMapping("/plataforma/deletar/{id}")
    public String deletarPlataforma(@PathVariable int id, RedirectAttributes ra) {
        try {
            pdao.delete(id);
            ra.addFlashAttribute("mensagem", "Plataforma removida com sucesso!");
        } catch (Exception e) {
            ra.addFlashAttribute("erro", "Erro ao excluir plataforma: " + e.getMessage());
        }
        return "redirect:/plataforma";
    }

    @GetMapping("/plataforma/{id}/jogadores")
    @ResponseBody
    public List<JogadorDTO> listarJogadoresPorPlataforma(@PathVariable Long id) {
        List<Jogador> jogadores = jdao.findByPlataforma(Math.toIntExact(id));
        return jogadores.stream()
                .map(j -> new JogadorDTO(j.getId(), j.getNome(), j.getEmail()))
                .toList();
    }



}
