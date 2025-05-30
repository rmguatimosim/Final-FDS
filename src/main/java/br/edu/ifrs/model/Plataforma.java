package br.edu.ifrs.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="plataforma")
public class Plataforma {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false, length = 100, unique = true)
    private String nome;
    @Column(nullable = false)
    private TipoPlataforma tipo;
    @Column(nullable = false, length = 150)
    private String proprietaria;
    @Column(nullable = false, unique = true, length = 150)
    private String email;

    @OneToMany(mappedBy = "plataforma")
    private List<Jogador> jogadores;

    @ManyToMany
    @JoinTable(name="plataforma_jogo",
            joinColumns = @JoinColumn(name="idPlataforma"),
            inverseJoinColumns = @JoinColumn(name="idJogo"))
    private List<Jogo> jogosDisponiveis;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public TipoPlataforma getTipo() {
        return tipo;
    }

    public void setTipo(TipoPlataforma tipo) {
        this.tipo = tipo;
    }

    public String getProprietaria() {
        return proprietaria;
    }

    public void setProprietaria(String proprietaria) {
        this.proprietaria = proprietaria;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Jogador> getJogadores() {
        return jogadores;
    }

    public void setJogadores(List<Jogador> jogadores) {
        this.jogadores = jogadores;
    }

    public List<Jogo> getJogosDisponiveis() {
        return jogosDisponiveis;
    }

    public void setJogosDisponiveis(List<Jogo> jogosDisponiveis) {
        this.jogosDisponiveis = jogosDisponiveis;
    }
}

//plataforma(id, nome, tipo, proprietária, email
