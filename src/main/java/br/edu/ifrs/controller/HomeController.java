package br.edu.ifrs.controller;


import br.edu.ifrs.connectionFactory.ConnectionBD;
import br.edu.ifrs.model.Jogador;
import br.edu.ifrs.persistence.JogadorDao;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;


@Controller
public class HomeController {

    @GetMapping("/")
    public String home(Model model) {

        return "redirect:/jogador";
    }


}
