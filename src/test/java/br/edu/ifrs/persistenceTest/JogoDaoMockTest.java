package br.edu.ifrs.persistenceTest;


import br.edu.ifrs.model.Jogo;
import br.edu.ifrs.persistence.JogoDao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class JogoDaoMockTest {
    private EntityManager em;
    private EntityTransaction et;
    JogoDao jdao;
    Jogo j;

    @BeforeEach
    public void init() {
        j = new Jogo("Elden Ring", 2022, "From Software", "Bandai Namco");
        em = mock(EntityManager.class);
        et = mock(EntityTransaction.class);
        when(em.getTransaction()).thenReturn(et);
        jdao = new JogoDao(em);
    }

    @Test
    @DisplayName("teste de insert com EM mockado")
    public void testInsert() {
        jdao.insert(j);
        verify(et).begin();
        verify(em).persist(j);
        verify(et).commit();
    }

    @Test
    @DisplayName("teste de delete com EM mockado")
    public void testDelete() {
        when(em.find(Jogo.class, 2)).thenReturn(j);
        j.setId(2);
        jdao.delete(2);
        verify(et).begin();
        verify(em).remove(j);
        verify(et).commit();
    }

    @Test
    @DisplayName("teste de throw da função Delete")
    public void testDeleteThrow() {
        when(em.find(Jogo.class, 99)).thenReturn(null);
        IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            jdao.delete(99);
        });
        Assertions.assertEquals("Não foi encontrado jogo com ID 99", thrown.getMessage());
        verify(et, never()).begin();
        verify(em, never()).remove(any());
        verify(et, never()).commit();
    }

    @Test
    @DisplayName("teste de update com EM mockado")
    public void testUpdate (){
        jdao.update(j);
        verify(et).begin();
        verify(em).merge(j);
        verify(et).commit();
    }

    @Test
    @DisplayName("teste de find com EM mockado")
    public void testFind(){
        when(em.find(Jogo.class, 2)).thenReturn(j);
        Jogo novo = jdao.find(2);
        Assertions.assertEquals(j, novo);
    }

}
