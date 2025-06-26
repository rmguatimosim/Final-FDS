package br.edu.ifrs.controller;

import br.edu.ifrs.connectionFactory.ConnectionBD;
import br.edu.ifrs.model.Jogador;
import br.edu.ifrs.model.Jogo;
import br.edu.ifrs.model.Plataforma;
import br.edu.ifrs.model.TipoPlataforma;
import br.edu.ifrs.persistence.JogadorDao;
import br.edu.ifrs.persistence.JogoDao;
import br.edu.ifrs.persistence.PlataformaDao;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;


import java.time.LocalDate;

@Component
public class Seeds {

    @PostConstruct
    public void carregaJogadores() {
        JogadorDao jdao = new JogadorDao(ConnectionBD.connection("prodUnit"));
        if(jdao.findAll(0,1).isEmpty()){
            jdao.insert(new Jogador("Rafael", "rafael@gmail.com", "62472674368", LocalDate.of(1990,7,6)));
            jdao.insert(new Jogador("Caroline", "carol@gmail.com", "66674058661", LocalDate.of(1990,5,7)));
            jdao.insert(new Jogador("Zé Gordino", "gordo@gmail.com", "78230124728", LocalDate.of(2011,8,16)));
        }
        JogoDao jodao = new JogoDao(ConnectionBD.connection("prodUnit"));
        if(jodao.findAll(0,1).isEmpty()){
            jodao.insert(new Jogo("Elden Ring", 2022, "From Software", "Bandai Namco"));
            jodao.insert(new Jogo("Stardew Valley", 2016, "ConcernedApe", "ConcernedApe"));
        }
        PlataformaDao pdao = new PlataformaDao(ConnectionBD.connection("prodUnit"));
        if(pdao.findAll(0,1).isEmpty()){
            pdao.insert(new Plataforma("PlayStation", TipoPlataforma.CONSOLE, "Sony", "sony@gmail.com"));
            pdao.insert(new Plataforma("Xbox", TipoPlataforma.CONSOLE, "Microsoft", "microsoft@outlook.com"));
            pdao.insert(new Plataforma("Epic Games", TipoPlataforma.REPOSITORIO, "Epic", "epicfail@uahoo.com"));
        }
    }

}
