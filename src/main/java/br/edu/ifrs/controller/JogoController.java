package br.edu.ifrs.controller;


import br.edu.ifrs.connectionFactory.ConnectionBD;
import br.edu.ifrs.form.JogoForm;
import br.edu.ifrs.model.Jogo;
import br.edu.ifrs.persistence.JogoDao;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class JogoController {

    private final JogoDao jdao = new JogoDao(ConnectionBD.connection("prodUnit"));
    @GetMapping("/jogo")
    public String listarJogos(Model model){
        List<Jogo> jogos = jdao.findAll(0,10);
        model.addAttribute("jogos", jogos);
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
            return listarJogos(model);
        }
    }
    @PostMapping("/jogo/deletar/{id}")
    public String deletarJogo(@PathVariable int id, RedirectAttributes redirectAttributes){
        try{
            jdao.delete(id);
        } catch(Exception e){
            redirectAttributes.addFlashAttribute("erro", "Erro ao excluir jogador: " + e.getMessage());
        }
        return "redirect:/jogo";
    }
}
