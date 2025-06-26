package br.edu.ifrs.form;

public class PlataformaForm {
    private Integer id;
    private String nome;
    private String tipo;
    private String proprietaria;
    private String email;

    // Getters
    public Integer getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getTipo() {
        return tipo;
    }

    public String getProprietaria() {
        return proprietaria;
    }

    public String getEmail() {
        return email;
    }

    // Setters
    public void setId(Integer id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setProprietaria(String proprietaria) {
        this.proprietaria = proprietaria;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}