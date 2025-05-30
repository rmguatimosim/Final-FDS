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


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getAnoLancamento() {
        return anoLancamento;
    }

    public void setAnoLancamento(int anoLancamento) {
        this.anoLancamento = anoLancamento;
    }

    public String getDesenvolvedora() {
        return desenvolvedora;
    }

    public void setDesenvolvedora(String desenvolvedora) {
        this.desenvolvedora = desenvolvedora;
    }

    public String getPublicadora() {
        return publicadora;
    }

    public void setPublicadora(String publicadora) {
        this.publicadora = publicadora;
    }

    public List<Plataforma> getPlataformas() {
        return plataformas;
    }

    public void setPlataformas(List<Plataforma> plataformas) {
        this.plataformas = plataformas;
    }
}


//jogo(id, titulo, ano_lancamento, desenvolvedora, publicadora