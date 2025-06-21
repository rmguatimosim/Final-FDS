package br.edu.ifrs.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="jogo")
public class Jogo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    private String titulo;
    @Column(name="ano_lancamento", nullable = false)
    private int anoLancamento;
    @Column(nullable = false, length = 100)
    private String desenvolvedora;
    @Column(nullable = false, length = 100)
    private String publicadora;

    @ManyToMany(mappedBy = "jogosDisponiveis")
    private List<Plataforma> plataformas;

    //construtores
    public Jogo(){

    }
    public Jogo(String titulo, int anoLancamento, String desenvolvedora, String publicadora){
        setTitulo(titulo);
        setAnoLancamento(anoLancamento);
        setDesenvolvedora(desenvolvedora);
        setPublicadora(publicadora);
    }
    //construtor para retorno de busca no banco de dados
    public Jogo(int id, String titulo, int anoLancamento, String desenvolvedora, String publicadora){
        this(titulo, anoLancamento, desenvolvedora, publicadora);
        setId(id);
    }

    //utilidades
    //função que valida ano de lançamento
    private boolean validaAnoLancamento(int anoLancamento){
        return anoLancamento <= 2050 && anoLancamento > 1969;
    }

    //getters
    public int getId() {return id;}
    public String getTitulo() {return titulo;}
    public int getAnoLancamento() {return anoLancamento;}
    public String getDesenvolvedora() {return desenvolvedora;}
    public String getPublicadora() {return publicadora;}
    public List<Plataforma> getPlataformas() {return plataformas;}

    //setters
    public void setId(int id) {
        this.id = id;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setAnoLancamento(int anoLancamento) {
        if(validaAnoLancamento(anoLancamento)){
            this.anoLancamento = anoLancamento;
        }
        else {
            throw new IllegalArgumentException("Ano de lançamento inválido.");
        }
    }

    public void setDesenvolvedora(String desenvolvedora) {
        this.desenvolvedora = desenvolvedora;
    }

    public void setPublicadora(String publicadora) {
        this.publicadora = publicadora;
    }

    public void setPlataformas(List<Plataforma> plataformas) {
        this.plataformas = plataformas;
    }
}


