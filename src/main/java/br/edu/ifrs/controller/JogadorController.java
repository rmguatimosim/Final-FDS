package br.edu.ifrs.controller;

import br.edu.ifrs.connectionFactory.ConnectionBD;
import br.edu.ifrs.form.JogadorForm;
import br.edu.ifrs.form.JogoDTO;
import br.edu.ifrs.model.Jogador;
import br.edu.ifrs.model.Jogo;
import br.edu.ifrs.persistence.JogadorDao;
import br.edu.ifrs.persistence.JogoDao;
import br.edu.ifrs.persistence.PlataformaDao;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import java.math.MathContext;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
public class JogadorController {
    private final JogadorDao jdao = new JogadorDao(ConnectionBD.connection("prodUnit"));
    private final PlataformaDao pdao = new PlataformaDao(ConnectionBD.connection("prodUnit"));
    private final JogoDao jodao = new JogoDao(ConnectionBD.connection("prodUnit"));

    @GetMapping("/jogador")
    public String listarJogadores(Model model){
        List<Jogador> jogadores = jdao.findAll(0,10);
        model.addAttribute("jogadores", jogadores);
        model.addAttribute("plataformas", pdao.findAll(0,10));
        model.addAttribute("jogos", jodao.findAll(0,10));
        return "jogador";
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
            return listarJogadores(model);
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
    public String atribuirPlataforma(@RequestParam Long jogadorId,
                                     @RequestParam Long plataformaId,
                                     RedirectAttributes ra) {
        Jogador j = jdao.find(Math.toIntExact(jogadorId));
        j.setPlataforma(pdao.find(Math.toIntExact(plataformaId)));
        jdao.update(j);
        ra.addFlashAttribute("mensagem", "Plataforma atribuída com sucesso!");
        return "redirect:/jogador";
    }

    @GetMapping("/jogador/{id}/jogos")
    @ResponseBody
    public List<JogoDTO> listarJogosDoJogador(@PathVariable Long id) {
        List<Jogo> jogos = jdao.find(Math.toIntExact(id)).getJogos();
        return jogos.stream()
                .map(j -> new JogoDTO(j.getId(), j.getTitulo(), j.getAnoLancamento()))
                .toList();
    }

    @PostMapping("/jogador/incluir-jogo")
    public String incluirJogo(@RequestParam Long jogadorId, @RequestParam Long jogoId, RedirectAttributes re) {
        Jogador j = jdao.find(Math.toIntExact(jogadorId));
        Jogo jogo = jodao.find(Math.toIntExact(jogoId));
        List<Jogo> lista = j.getJogos();
        if(lista.contains(jogo)){
            re.addFlashAttribute("erro", "Jogo já consta na lista!");
        }
        else{
            lista.add(jogo);
            re.addFlashAttribute("mensagem", "Jogo incluído com sucesso!");

        }
        return "redirect:/jogador";
    }


}

