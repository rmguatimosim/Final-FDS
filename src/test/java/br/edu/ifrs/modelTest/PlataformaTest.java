package br.edu.ifrs.modelTest;

import br.edu.ifrs.model.Plataforma;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PlataformaTest {
    Plataforma p =  new Plataforma();

    @Test
    @DisplayName("Teste da função setEmail")
    public void testeSetEmail(){
        String email = "empresa@emp.com";
        p.setEmail(email);
        Assertions.assertEquals(email, p.getEmail());
    }

    @Test
    @DisplayName("Teste de throws da função setEmail")
    public void testSetEmailThrows(){
        Assertions.assertAll(
                "teste de throw setEmail",
                ()->{Assertions.assertThrows(IllegalArgumentException.class, ()->{p.setEmail("");}, "teste de email em branco");},
                ()->{Assertions.assertThrows(IllegalArgumentException.class, ()->{p.setEmail("empresaemp.com");}, "teste de email sem @");},
                ()->{Assertions.assertThrows(IllegalArgumentException.class, ()->{p.setEmail("empresa@empcom");}, "teste de email sem . após @");},
                ()->{Assertions.assertThrows(IllegalArgumentException.class, ()->{p.setEmail("empresa@emp.");}, "teste de email terminado em .");}
        );
    }
}
