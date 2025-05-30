package br.edu.ifrs.model;


import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name="jogador")
public class Jogador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false, length = 100)
    private String nome;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false, unique = true, length = 14)
    private String cpf;
    @Column(name="data_nascimento", nullable = false)
    private LocalDate dataNascimento;

    @ManyToOne
    @JoinColumn(name="idPlataforma")
    private Plataforma plataforma;

    @ManyToMany
    @JoinTable(name="jogador_jogo",
        joinColumns = @JoinColumn(name="idJogador"),
        inverseJoinColumns = @JoinColumn(name="idJogo"))
    private List<Jogo> jogos;

    public boolean testaCPF(String cpf) {
        boolean b = true;
        if(cpf.length() != 14) b = false;
        else {
            if(cpf.charAt(3) != '.' || cpf.charAt(7) != '.' || cpf.charAt(11) != '-') b = false;
            if(!cpf.substring(0, 3).matches("[0-9]*")) b = false;
            if(!cpf.substring(4, 7).matches("[0-9]*")) b = false;
            if(!cpf.substring(8, 11).matches("[0-9]*")) b = false;
            if(!cpf.substring(12).matches("[0-9]*")) b = false;
        }
        return b;
    }
    public boolean validaCPF(String cpf) {
        int d1 = Integer.parseInt(cpf, 0, 1, 10);
        int d2 = Integer.parseInt(cpf, 1, 2, 10);
        int d3 = Integer.parseInt(cpf, 2, 3, 10);
        int d4 = Integer.parseInt(cpf, 4, 5, 10);
        int d5 = Integer.parseInt(cpf, 5, 6, 10);
        int d6 = Integer.parseInt(cpf, 6, 7, 10);
        int d7 = Integer.parseInt(cpf, 8, 9, 10);
        int d8 = Integer.parseInt(cpf, 9, 10, 10);
        int d9 = Integer.parseInt(cpf, 10, 11, 10);
        int d10 = Integer.parseInt(cpf, 12, 13, 10);
        int d11 = Integer.parseInt(cpf, 13, 14, 10);
        int dig1 = ((d1*10)+(d2*9)+(d3*8)+(d4*7)+(d5*6)+(d6*5)+(d7*4)+(d8*3)+(d9*2))%11;
        if(dig1>=10) dig1 = 0;
        if(11 - dig1 != d10 ) {
            return false;
        }
        int dig2 = ((d1*11)+(d2*10)+(d3*9)+(d4*8)+(d5*7)+(d6*6)+(d7*5)+(d8*4)+(d9*3)+(d10*2))%11;
        if(dig2>=10) dig2 = 0;
        if(11 - dig2 != d11 && dig2!=0) {
            return false;
        }
        return true;
    }

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        if(testaCPF(cpf) && validaCPF(cpf)) {
            this.cpf = cpf;
        }
        else {
            throw new IllegalArgumentException("CPF informado inváido!");
        }
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public Plataforma getPlataforma() {
        return plataforma;
    }

    public void setPlataforma(Plataforma plataforma) {
        this.plataforma = plataforma;
    }

    public List<Jogo> getJogos() {
        return jogos;
    }

    public void setJogos(List<Jogo> jogos) {
        this.jogos = jogos;
    }
}


