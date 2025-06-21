package br.edu.ifrs.modelTest;

import br.edu.ifrs.model.Jogo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class JogoTest {
    Jogo j = new Jogo();

    @Test
    @DisplayName("Teste da função setAnoLançamento")
    public void testSetAnoLançamento(){
        int ano = 2022;
        j.setAnoLancamento(ano);
        Assertions.assertEquals(ano, j.getAnoLancamento());
    }

    @Test
    @DisplayName("Teste de throw da função setAnoLancamento")
    public void testSetAnoLancamentoThrows(){
        Assertions.assertAll(
                "Teste de throw de setAnoLancamento",
                ()->{Assertions.assertThrows(IllegalArgumentException.class, ()->{j.setAnoLancamento(2100);},"teste de ano após 2050");},
                ()->{Assertions.assertThrows(IllegalArgumentException.class, ()->{j.setAnoLancamento(1850);},"teste de ano pré 1970");}
        );
    }

}
