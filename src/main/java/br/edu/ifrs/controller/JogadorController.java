package br.edu.ifrs.controller;

import br.edu.ifrs.connectionFactory.ConnectionBD;
import br.edu.ifrs.form.JogadorForm;
import br.edu.ifrs.form.JogoDTO;
import br.edu.ifrs.model.Jogador;
import br.edu.ifrs.model.Jogo;
import br.edu.ifrs.model.Plataforma;
import br.edu.ifrs.persistence.JogadorDao;
import br.edu.ifrs.persistence.JogoDao;
import br.edu.ifrs.persistence.PlataformaDao;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import java.time.LocalDate;
import java.util.List;

@Controller
public class JogadorController {
    private final JogadorDao jdao = new JogadorDao(ConnectionBD.connection("prodUnit"));
    private final PlataformaDao pdao = new PlataformaDao(ConnectionBD.connection("prodUnit"));
    private final JogoDao jodao = new JogoDao(ConnectionBD.connection("prodUnit"));



    @GetMapping("/jogador")
    public String listarJogadores(@RequestParam(defaultValue = "0") int offset,
                                  @RequestParam(defaultValue = "10") int limit,
                                  Model model) {
        List<Jogador> jogadores = jdao.findAll(offset, limit);
        model.addAttribute("jogadores", jogadores);
        model.addAttribute("offset", offset);
        model.addAttribute("limit", limit);
        model.addAttribute("plataformas", pdao.findAll(0, 10));
        model.addAttribute("jogos", jodao.findAll(0, 10));
        return "jogador";
    }


    @GetMapping("/jogador/{jogadorId}/jogos-disponiveis")
    @ResponseBody
    public List<JogoDTO> listarJogosDisponiveisParaJogador(@PathVariable int jogadorId) {
        Jogador jogador = jdao.find(jogadorId);
        Plataforma plataforma = jogador.getPlataforma();
        if (plataforma == null) return List.of();
        return plataforma.getJogosDisponiveis().stream()
                .filter(jogo -> !jogador.getJogos().contains(jogo))
                .map(j -> new JogoDTO(j.getId(), j.getTitulo(), j.getAnoLancamento()))
                .toList();
    }

    @PostMapping("/jogador/salvar")
    public String salvarOuEditar(@ModelAttribute JogadorForm form,
                                 RedirectAttributes ra, Model model) {
        try {
            LocalDate data = LocalDate.parse(form.getDataNascimento());
            Jogador j;

            if (form.getId() != null && form.getId() > 0) {
                j = jdao.find(Math.toIntExact(form.getId()));
                j.setNome(form.getNome());
                j.setEmail(form.getEmail());
                j.setCpf(form.getCpf());
                j.setDataNascimento(data);
                jdao.update(j);
                ra.addFlashAttribute("mensagem", "Jogador atualizado com sucesso!");
            } else {
                j = new Jogador(form.getNome(), form.getEmail(), form.getCpf(), data);
                jdao.insert(j);
                ra.addFlashAttribute("mensagem", "Jogador cadastrado com sucesso!");
            }
            return "redirect:/jogador";
        } catch (IllegalArgumentException e) {
            model.addAttribute("form", form);
            model.addAttribute("erroModal", "Erro ao salvar jogador: " + e.getMessage());
            model.addAttribute("abrirModal", true);
            return listarJogadores(0,10,model);
        }
    }

    @PostMapping("/jogador/deletar/{id}")
    public String deletarJogador(@PathVariable int id, RedirectAttributes ra) {
        try {
            jdao.delete(id);
            ra.addFlashAttribute("mensagem", "Jogador removido com sucesso!");
        } catch (Exception e) {
            ra.addFlashAttribute("erro", "Erro ao excluir jogador: " + e.getMessage());
        }
        return "redirect:/jogador";
    }

    @PostMapping("/jogador/atribuir-plataforma")
    public String atribuirPlataforma(@RequestParam int jogadorId,
                                     @RequestParam int plataformaId,
                                     RedirectAttributes ra) {
        Jogador j = jdao.find(jogadorId);
        j.setPlataforma(pdao.find(plataformaId));
        jdao.update(j);
        ra.addFlashAttribute("mensagem", "Plataforma atribuída com sucesso!");
        return "redirect:/jogador";
    }

    @GetMapping("/jogador/{id}/jogos")
    @ResponseBody
    public List<JogoDTO> listarJogosDoJogador(@PathVariable int id) {
        List<Jogo> jogos = jdao.find(id).getJogos();
        return jogos.stream()
                .map(j -> new JogoDTO(j.getId(), j.getTitulo(), j.getAnoLancamento()))
                .toList();
    }

    @PostMapping("/jogador/incluir-jogo")
    public String incluirJogo(@RequestParam int jogadorId, @RequestParam int jogoId, RedirectAttributes re) {
        Jogador j = jdao.find(jogadorId);
        if(j.getJogos().contains(jodao.find(jogoId))){
            re.addFlashAttribute("erro", "Jogo já consta na lista!");
        }
        else{
            j.getJogos().add(jodao.find(jogoId));
            jdao.update(j);
            re.addFlashAttribute("mensagem", "Jogo incluído com sucesso!");
        }
        return "redirect:/jogador";
    }
    @DeleteMapping("/jogador/{jogadorId}/remover-jogo/{jogoId}")
    @ResponseBody
    public ResponseEntity<?> removerJogo(@PathVariable int jogadorId, @PathVariable int jogoId) {
        Jogador j = jdao.find(jogadorId);
        Jogo jogo = jodao.find(jogoId);
        System.out.println(j.getJogos().contains(jodao.find(jogoId)));
        j.getJogos().remove(jodao.find(jogoId));
        jdao.update(j);
        return ResponseEntity.ok().build();
    }





}

