package br.edu.ifrs.modelTest;

import br.edu.ifrs.model.Jogador;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class JogadorTest {
    Jogador jo = new Jogador();

    @Test
    @DisplayName("Teste de validador de nome")
    public void testValidaNome(){
        Assertions.assertAll(
                "Teste de validação de nome",
                () -> Assertions.assertTrue(jo.validaNome("Rafael"), "teste de nome válido"),
                () -> Assertions.assertTrue(jo.validaNome("Rafael Augusto"), "teste de nome composto"),
                () -> Assertions.assertFalse(jo.validaNome(""), "teste de nome em branco"),
                () -> Assertions.assertFalse(jo.validaNome("Rafael2123"), "teste de nome com caractere inválido")
        );
    }

    @Test
    @DisplayName("Teste de validador de email")
    public void testValidaEmail(){
        Assertions.assertAll(
                "Teste de validação de email",
                () -> {Assertions.assertTrue(jo.validaEmail("rafael@rafael.com"), "teste de email válido");},
                () -> {Assertions.assertFalse(jo.validaEmail(""), "teste entrada de email em branco");},
                () -> {Assertions.assertFalse(jo.validaEmail("rafaelgmail.com"),"teste email sem @");},
                () -> {Assertions.assertFalse(jo.validaEmail("rafael@gmailcom"), "teste email sem . após o @");},
                () -> {Assertions.assertFalse(jo.validaEmail("rafael@gmail.com."), "teste email terminado em .");}
        );
    }

    @Test
    @DisplayName("Teste de validador de cpf")
    public void testValidaCPF(){
        Assertions.assertAll(
                "Teste de validador de cpf",
                () -> {Assertions.assertTrue(jo.validaCPF("01658023080"), "teste de cpf válido");},
                () ->  {Assertions.assertFalse(jo.validaCPF(""), "teste de cpf em branco");},
                () -> {Assertions.assertFalse(jo.validaCPF("003"), "teste de cpf de tamanho inválido");},
                () -> {Assertions.assertFalse(jo.validaCPF("Rafael"), "teste de cpf com caractere inválido");},
                () -> {Assertions.assertFalse(jo.validaCPF("00100200300"), "teste cpf incorreto");}
        );
    }

    @Test@DisplayName("teste de validador de data de nascimento")
    public void testValidaDataNasc(){
        Assertions.assertAll(
                "teste de validador de data de nascimento",
                () -> {Assertions.assertTrue(jo.validaDataNasc(LocalDate.of(1990,7,6)), "teste de data válida");},
                () -> {Assertions.assertFalse(jo.validaDataNasc(LocalDate.of(2099,12,1)), "teste de data no futuro");},
                () -> {Assertions.assertFalse(jo.validaDataNasc(LocalDate.of(1850,6,5)), "teste de data anterior a 1900");}
        );
    }

    @Test
    @DisplayName("Teste de throws da função setNome")
    public void testSetNomeThrows(){
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            jo.setNome("rafael1");
        });
    }

    @Test
    @DisplayName("Teste da função setNome funcionando")
    public void testSetNome(){
        String nome = "Rafael";
        jo.setNome(nome);
        Assertions.assertEquals(nome, jo.getNome());
    }

    @Test
    @DisplayName("Teste de throws da função setEmail")
    public void testSetEmailThrows(){
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            jo.setEmail("email Inválido");
        });
    }

    @Test
    @DisplayName("Teste da função setEmail funcionando")
    public void testSetEmail(){
        String email = "rafael@gmail.com";
        jo.setEmail(email);
        Assertions.assertEquals(email, jo.getEmail());
    }

    @Test
    @DisplayName("Teste de throws da função setCpf")
    public void testSetCpfThrows(){
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            jo.setCpf("Cpf Inválido");
        });
    }

    @Test
    @DisplayName("Teste da função setCpf funcionando")
    public void testSetCpf(){
        String cpf = "01658023080";
        jo.setCpf(cpf);
        Assertions.assertEquals(cpf, jo.getCpf());
    }

    @Test
    @DisplayName("teste de throws da função setDataNascimento")
    public void testSetDataNascimentoThrows(){
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            jo.setDataNascimento(LocalDate.of(1750,6,5));
        });
    }

    @Test
    @DisplayName("teste da função setDataNascimento funcionando")
    public void testSetDataNascimento(){
        LocalDate data = LocalDate.of(1990,7,6);
        jo.setDataNascimento(data);
        Assertions.assertEquals(data, jo.getDataNascimento());
    }


}
