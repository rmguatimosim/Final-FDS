package br.edu.ifrs.controller;

import br.edu.ifrs.connectionFactory.ConnectionBD;
import br.edu.ifrs.form.JogadorForm;
import br.edu.ifrs.model.Jogador;
import br.edu.ifrs.persistence.JogadorDao;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import java.time.LocalDate;
import java.util.List;

@Controller
public class JogadorController {
    private final JogadorDao jdao = new JogadorDao(ConnectionBD.connection("prodUnit"));

    @GetMapping("/jogador")
    public String listarJogadores(Model model){
        List<Jogador> jogadores = jdao.findAll(0,10);
        model.addAttribute("jogadores", jogadores);
        return "jogador";
    }

    @PostMapping("/jogador/salvar")
    public String salvarOuEditar(@RequestParam(required = false) Long id,
                                 @RequestParam String nome,
                                 @RequestParam String email,
                                 @RequestParam String cpf,
                                 @RequestParam String dataNascimento,
                                 RedirectAttributes redirectAttributes, Model model) {
        try {
            LocalDate data = LocalDate.parse(dataNascimento);
            Jogador jogador;

            if (id != null) {
                jogador = jdao.find(Math.toIntExact(id));
                jogador.setNome(nome);
                jogador.setEmail(email);
                jogador.setCpf(cpf);
                jogador.setDataNascimento(data);
                jdao.update(jogador);
                redirectAttributes.addFlashAttribute("mensagem", "Jogador atualizado com sucesso!");
            } else {
                jogador = new Jogador(nome, email, cpf, data);
                jdao.insert(jogador);
                redirectAttributes.addFlashAttribute("mensagem", "Jogador cadastrado com sucesso!");
            }
        } catch (IllegalArgumentException e) {
            JogadorForm form = new JogadorForm();
            form.setId(id);
            form.setNome(nome);
            form.setEmail(email);
            form.setCpf(cpf);
            form.setDataNascimento(dataNascimento);

            model.addAttribute("form", form);
            model.addAttribute("erroModal", "Erro ao salvar jogador: " + e.getMessage());
            model.addAttribute("abrirModal", true);
            return listarJogadores(model);

        }

        return "redirect:/jogador";
    }

    @PostMapping("/jogador/deletar/{id}")
    public String deletarJogador(@PathVariable int id, RedirectAttributes redirectAttributes) {
        try {
            jdao.delete(id);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao excluir jogador: " + e.getMessage());
        }
        return "redirect:/jogador";
    }


}

