package br.edu.ifrs.modelTest;

import br.edu.ifrs.model.Jogador;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class JogadorTest {
    Jogador jo = new Jogador();

    @Test
    @DisplayName("Teste da função setNome")
    public void testSetNome(){
        String nome = "Zé Gordino";
        jo.setNome(nome);
        Assertions.assertEquals(nome, jo.getNome());
    }

    @Test
    @DisplayName("Teste de throws da função setNome")
    public void testSetNomeThrows(){
        Assertions.assertAll(
                "Teste de throw de setNome",
                () -> {Assertions.assertThrows(IllegalArgumentException.class, () -> {jo.setNome("");}, "teste de nome em branco");},
                () -> {Assertions.assertThrows(IllegalArgumentException.class, () -> {jo.setNome("Rafael123");}, "teste de nome caractere inválido");}
        );
    }

    @Test
    @DisplayName("Teste da função setEmail")
    public void testSetEmail(){
        String email = "rafael@gmail.com";
        jo.setEmail(email);
        Assertions.assertEquals(email, jo.getEmail());
    }

    @Test
    @DisplayName("Teste de throws da função setEmail")
    public void testSetEmailThrows(){
        Assertions.assertAll(
                "Teste de throw setEmail",
                () -> {Assertions.assertThrows(IllegalArgumentException.class, () -> {jo.setEmail("");}, "Teste de email em branco");},
                () -> {Assertions.assertThrows(IllegalArgumentException.class, () -> {jo.setEmail("rafaelgmail.com");}, "Teste de email sem @");},
                () -> {Assertions.assertThrows(IllegalArgumentException.class, () -> {jo.setEmail("rafael@gmailcom");}, "Teste de email sem . após o @");},
                () -> {Assertions.assertThrows(IllegalArgumentException.class, () -> {jo.setEmail("rafael@gmail.");}, "Teste de email terminado em .");}
        );
    }

    @Test
    @DisplayName("Teste da função setCpf")
    public void testSetCpf(){
        String cpf = "01658023080";
        jo.setCpf(cpf);
        Assertions.assertEquals(cpf, jo.getCpf());
    }

    @Test
    @DisplayName("Teste de throws da função setCpf")
    public void testSetCpfThrows(){
        Assertions.assertAll(
                "Teste de throw setCpf",
                () -> {Assertions.assertThrows(IllegalArgumentException.class, () -> {jo.setCpf("");}, "Teste de cpf em branco");},
                () -> {Assertions.assertThrows(IllegalArgumentException.class, () -> {jo.setCpf("003");}, "Teste de cpf com menos de 11 digitos");},
                () -> {Assertions.assertThrows(IllegalArgumentException.class, () -> {jo.setCpf("111111111111");}, "Teste de cpf com mais de 11 digitos");},
                () -> {Assertions.assertThrows(IllegalArgumentException.class, () -> {jo.setCpf("Rafael");}, "Teste de cpf com caractere inválido");},
                () -> {Assertions.assertThrows(IllegalArgumentException.class, () -> {jo.setCpf("00100200300");}, "Teste de cpf com número incorreto");}
        );
    }

    @Test
    @DisplayName("teste da função setDataNascimento")
    public void testSetDataNascimento(){
        LocalDate data = LocalDate.of(1990,7,6);
        jo.setDataNascimento(data);
        Assertions.assertEquals(data, jo.getDataNascimento());
    }

    @Test
    @DisplayName("teste de throws da função setDataNascimento")
    public void testSetDataNascimentoThrows(){
        Assertions.assertAll(
                "Teste de throw setDataNascimento",
                () -> {Assertions.assertThrows(IllegalArgumentException.class, () -> {jo.setDataNascimento(LocalDate.of(2099,12,1));}, "teste de data no futuro");},
                () -> {Assertions.assertThrows(IllegalArgumentException.class, () -> {jo.setDataNascimento(LocalDate.of(1850,6,5));}, "teste de data anterior a 1900");}
        );
    }

    @Test
    @DisplayName("teste função calculaIdade")
    public void testCalculaIdade(){
        LocalDate data = LocalDate.of(1990,7,6);
        jo.setDataNascimento(data);
        Assertions.assertEquals(35, jo.calculaIdade());
    }

    @Test
    @DisplayName("teste de idade no objeto")
    public void testIdadeObj(){
        LocalDate data = LocalDate.of(1990,7,6);
        jo.setDataNascimento(data);
        Assertions.assertEquals(35, jo.getIdade());
    }

}
