package br.edu.ifrs.controller;


import br.edu.ifrs.connectionFactory.ConnectionBD;
import br.edu.ifrs.form.JogadorDTO;
import br.edu.ifrs.form.JogoDTO;
import br.edu.ifrs.form.PlataformaDTO;
import br.edu.ifrs.form.PlataformaForm;
import br.edu.ifrs.model.Jogador;
import br.edu.ifrs.model.Jogo;
import br.edu.ifrs.model.Plataforma;
import br.edu.ifrs.model.TipoPlataforma;
import br.edu.ifrs.persistence.JogadorDao;
import br.edu.ifrs.persistence.JogoDao;
import br.edu.ifrs.persistence.PlataformaDao;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class PlataformaController {
    private final PlataformaDao pdao = new PlataformaDao(ConnectionBD.connection("prodUnit"));
    private final JogadorDao jdao = new JogadorDao(ConnectionBD.connection("prodUnit"));
    private final JogoDao jodao = new JogoDao(ConnectionBD.connection("prodUnit"));

    //carrega página inicial de plataforma
    @GetMapping("/plataforma")
    public String listarPlataformas(Model model) {
        model.addAttribute("plataformas", pdao.findAll(0,100));
        model.addAttribute("jogos", jodao.findAll(0,100));
        model.addAttribute("jogadores", jdao.findAll(0,100));
        return "plataforma";
    }

    // salva plataforma no banco de dados
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

    //delete plataforma do banco de dados
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

    //lista jogadores no modal de cadastrar jogadores na plataforma
    @GetMapping("/plataforma/{id}/jogadores")
    @ResponseBody
    public List<JogadorDTO> listarJogadoresPorPlataforma(@PathVariable Long id) {
        List<Jogador> jogadores = jdao.findByPlataforma(Math.toIntExact(id));
        return jogadores.stream()
                .map(j -> new JogadorDTO(j.getId(), j.getNome(), j.getEmail()))
                .toList();
    }

    //lista jogos no modal de cadastrar jogos na plataforma
    @GetMapping("/plataforma/{id}/jogos")
    @ResponseBody
    public List<JogoDTO> listarJogos(@PathVariable int id) {
        List<Jogo> jogos = pdao.find(id).getJogosDisponiveis();
        return jogos.stream()
                .map(p -> new JogoDTO(p.getId(), p.getTitulo(), p.getAnoLancamento()))
                .toList();
    }

    //função para salvar jogo na listagem de jogos da plataforma no banco de dados
    @PostMapping("/plataforma/incluir-jogo")
    public String incluirJogo(@RequestParam int plataformaId, @RequestParam int jogoId, RedirectAttributes re) {
        Plataforma p = pdao.find(plataformaId);
        Jogo jogo = jodao.find(jogoId);
        //List<Jogo> lista = p.getJogosDisponiveis();
        if(p.getJogosDisponiveis().contains(jogo)){
            re.addFlashAttribute("erro", "Jogo já consta na lista!");
        }
        else{
            p.getJogosDisponiveis().add(jogo);
            pdao.update(p);
            re.addFlashAttribute("mensagem", "Jogo incluído com sucesso!");

        }
        return "redirect:/plataforma";
    }

    //função para deletar jogo da listagem de jogos da plataforma do banco de dados
    @DeleteMapping("/plataforma/{plataformaId}/remover-jogo/{jogoId}")
    @ResponseBody
    public ResponseEntity<?> removerJogo(@PathVariable int plataformaId, @PathVariable int jogoId) {
        Plataforma p = pdao.find(plataformaId);
        Jogo j = jodao.find(jogoId);
        if (p == null || j == null) {
            return ResponseEntity.notFound().build();
        }
        p.getJogosDisponiveis().remove(j);
        j.getPlataformas().remove(p);
        pdao.update(p);
        return ResponseEntity.ok().build();
    }


}
