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
            jdao.insert(new Jogador("Jinx", "gatuna@gmail.com", "38287782082", LocalDate.of(2020,11,23)));
            jdao.insert(new Jogador("Celso", "celso@hotmail.com", "12689735059", LocalDate.of(1989,3,25)));
        }
        JogoDao jodao = new JogoDao(ConnectionBD.connection("prodUnit"));
        if(jodao.findAll(0,1).isEmpty()){
            jodao.insert(new Jogo("Elden Ring", 2022, "From Software", "Bandai Namco"));
            jodao.insert(new Jogo("Stardew Valley", 2016, "ConcernedApe", "ConcernedApe"));
            jodao.insert(new Jogo("Fifa 2025", 2024, "EA Sports", "EA"));
            jodao.insert(new Jogo("DOTA", 2011, "Valve", "Valve"));
            jodao.insert(new Jogo("R.E.P.O", 2025, "semiwork", "semiwork"));
            jodao.insert(new Jogo("God of War Ragnarök", 2022, "Santa Monica Studio", "Sony Interactive Entertainment"));
            jodao.insert(new Jogo("Hogwarts Legacy", 2023, "Avalanche Software", "Warner Bros. Games"));
            jodao.insert(new Jogo("Resident Evil 4 Remake", 2023, "Capcom", "Capcom"));
            jodao.insert(new Jogo("Baldur's Gate 3", 2023, "Larian Studios", "Larian Studios"));
            jodao.insert(new Jogo("The Legend of Zelda: Tears of the Kingdom", 2023, "Nintendo EPD", "Nintendo"));
            jodao.insert(new Jogo("Marvel's Spider-Man 2", 2023, "Insomniac Games", "Sony Interactive Entertainment"));
            jodao.insert(new Jogo("Starfield", 2023, "Bethesda Game Studios", "Bethesda Softworks"));
            jodao.insert(new Jogo("Alan Wake 2", 2023, "Remedy Entertainment", "Epic Games Publishing"));

        }
        PlataformaDao pdao = new PlataformaDao(ConnectionBD.connection("prodUnit"));
        if(pdao.findAll(0,1).isEmpty()){
            pdao.insert(new Plataforma("PlayStation", TipoPlataforma.CONSOLE, "Sony", "sony@gmail.com"));
            pdao.insert(new Plataforma("Xbox", TipoPlataforma.CONSOLE, "Microsoft", "microsoft@outlook.com"));
            pdao.insert(new Plataforma("Epic Games", TipoPlataforma.REPOSITORIO, "Epic", "epicfail@uahoo.com"));
            pdao.insert(new Plataforma("Android", TipoPlataforma.MOBILE, "Google", "android@google.com"));

        }
    }

}
