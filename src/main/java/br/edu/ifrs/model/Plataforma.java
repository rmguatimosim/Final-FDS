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


    //construtores
    public Plataforma(){

    }
    public Plataforma(String nome, TipoPlataforma tipo, String proprietaria, String email){
        setNome(nome);
        setTipo(tipo);
        setProprietaria(proprietaria);
        setEmail(email);
    }
    //construtor para retorno de busca no banco de dados
    public Plataforma(int id, String nome, TipoPlataforma tipo, String proprietaria, String email){
        this(nome, tipo, proprietaria, email);
        setId(id);
    }

    //funções de validação
    //valida se email é válido
    private boolean validaEmail(String email){
        if(!email.contains("@") || email.isBlank()){
            return false;
        }
        String afterAt = email.substring(email.indexOf("@"));
        if(!afterAt.contains(".") || afterAt.endsWith(".")){
            return false;
        }
        return true;
    }

    //getters
    public int getId() {return id;}
    public String getNome() {return nome;}
    public TipoPlataforma getTipo() {return tipo;}
    public String getProprietaria() {return proprietaria;}
    public String getEmail() {return email;}
    public List<Jogador> getJogadores() {return jogadores;}
    public List<Jogo> getJogosDisponiveis() {return jogosDisponiveis;}

    //setters
    public void setId(int id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setTipo(TipoPlataforma tipo) {
        this.tipo = tipo;
    }

    public void setProprietaria(String proprietaria) {
        this.proprietaria = proprietaria;
    }

    public void setEmail(String email) {
        if(validaEmail(email)){
            this.email = email;
        }
        else{
            throw new IllegalArgumentException("Email inválido");
        }
    }

    public void setJogadores(List<Jogador> jogadores) {
        this.jogadores = jogadores;
    }

    public void setJogosDisponiveis(List<Jogo> jogosDisponiveis) {
        this.jogosDisponiveis = jogosDisponiveis;
    }
}


