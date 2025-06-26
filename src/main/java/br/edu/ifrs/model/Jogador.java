package br.edu.ifrs.model;


import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    @Column(nullable = false, unique = true, length = 11)
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


    //construtores
    public Jogador(){

    }
    public Jogador(String nome, String email, String cpf, LocalDate dataNasc){
        setNome(nome);
        setEmail(email);
        setCpf(cpf);
        setDataNascimento(dataNasc);
    }
    //construtor para retorno de busca do banco
    public Jogador(int id, String nome, String email, String cpf, LocalDate dataNasc){
        this(nome, email, cpf, dataNasc);
        setId(id);

    }


    //utilidades
    //função que verifica se CPF informado é válido.
    private boolean validaCPF(String cpf) {
        boolean b = true;
        if(cpf.length() != 11) b = false;
        else {
            if(!cpf.matches("[0-9]*")) b = false;
            else{
                int d1 = Integer.parseInt(cpf, 0, 1, 10);
                int d2 = Integer.parseInt(cpf, 1, 2, 10);
                int d3 = Integer.parseInt(cpf, 2, 3, 10);
                int d4 = Integer.parseInt(cpf, 3,4 , 10);
                int d5 = Integer.parseInt(cpf, 4, 5, 10);
                int d6 = Integer.parseInt(cpf, 5, 6, 10);
                int d7 = Integer.parseInt(cpf, 6, 7, 10);
                int d8 = Integer.parseInt(cpf, 7, 8, 10);
                int d9 = Integer.parseInt(cpf, 8, 9, 10);
                int d10 = Integer.parseInt(cpf, 9, 10, 10);
                int d11 = Integer.parseInt(cpf, 10, 11, 10);
                int dig1 = ((d1*10)+(d2*9)+(d3*8)
                        +(d4*7)+(d5*6)+(d6*5)+(d7*4)+(d8*3)+(d9*2))%11;
                if(dig1>=10) dig1 = 0;
                if(11 - dig1 != d10 ) {
                    b = false;
                }
                int dig2 = ((d1*11)+(d2*10)+(d3*9)
                        +(d4*8)+(d5*7)+(d6*6)+(d7*5)
                        +(d8*4)+(d9*3)+(d10*2))%11;
                if(dig2>=10) dig2 = 0;
                if(11 - dig2 != d11 && dig2!=0) {
                    b = false;
                }
            }
        }
        return b;
    }
    //função que verifica se nome não está em branco e é válido
    private boolean validaNome(String nome) {
        Pattern pattern = Pattern.compile("^[\\p{L}\\s]+$");
        Matcher matcher = pattern.matcher(nome);
        return matcher.matches();
    }
    //função que verifica se email é válido
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
    //função que verifica se data de nascimento é válida
    private boolean validaDataNasc(LocalDate dataNascimento){
        if(dataNascimento.isAfter(LocalDate.now()) || dataNascimento.getYear()<1900){
            return false;
        }
        return true;
    }
    //função para cálculo de idade baseado na data de nascimento
    public int calculaIdade(){
        return LocalDate.now().getYear() - this.dataNascimento.getYear();
    }

    //Getters
    public int getId() {return id;}
    public String getNome() {return nome;}
    public String getEmail() {return email;}
    public String getCpf() {return cpf;}
    public LocalDate getDataNascimento() {return dataNascimento;}
    public Plataforma getPlataforma() {return plataforma;}
    public List<Jogo> getJogos() {return jogos;}
    public int getIdade(){return calculaIdade();};

    //setters
    public void setId(int id) {
        this.id = id;
    }

    public void setNome(String nome) {
        if(!validaNome(nome) || nome.isEmpty()){
            throw new IllegalArgumentException("Nome inválido");
        }
        else{
            this.nome = nome;
        }
    }

    public void setEmail(String email) {
        if(validaEmail(email)){
            this.email = email;
        }
        else{
            throw new IllegalArgumentException("Email inválido");
        }
    }

    public void setCpf(String cpf) {
        if(validaCPF(cpf)) {
            this.cpf = cpf;
        }
        else {
            throw new IllegalArgumentException("CPF informado inváido!");
        }
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        if(validaDataNasc(dataNascimento)) {
            this.dataNascimento = dataNascimento;
        }
        else {
            throw new IllegalArgumentException("Data inválida.");
        }
    }

    public void setPlataforma(Plataforma plataforma) {this.plataforma = plataforma;}

    public void setJogos(List<Jogo> jogos) {
        this.jogos = jogos;
    }

}


