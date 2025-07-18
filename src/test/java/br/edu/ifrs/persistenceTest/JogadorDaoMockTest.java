package br.edu.ifrs.persistenceTest;

import br.edu.ifrs.model.Jogador;
import br.edu.ifrs.persistence.JogadorDao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.mockito.Mockito.*;

public class JogadorDaoMockTest {
    private EntityManager em;
    private EntityTransaction et;
    JogadorDao jdao;
    Jogador j;

    @BeforeEach
    public void init() {
        j = new Jogador("Rafael", "rafael@gmail.com", "01658023080", LocalDate.of(1990,7,6));
        em = mock(EntityManager.class);
        et = mock(EntityTransaction.class);
        when(em.getTransaction()).thenReturn(et);

        jdao = new JogadorDao(em);

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
        when(em.find(Jogador.class, 2)).thenReturn(j);
        j.setId(2);
        jdao.delete(2);
        verify(et).begin();
        verify(em).remove(j);
        verify(et).commit();
    }

    @Test
    @DisplayName("teste de throw da função Delete")
    public void testDeleteThrow() {
        when(em.find(Jogador.class, 99)).thenReturn(null);
        IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            jdao.delete(99);
        });
        Assertions.assertEquals("Não foi encontrado jogador com ID 99", thrown.getMessage());
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
        when(em.find(Jogador.class, 2)).thenReturn(j);
        Jogador novo = jdao.find(2);
        Assertions.assertEquals(j, novo);
    }

}



