package br.edu.ifrs.persistenceTest;

import br.edu.ifrs.model.Jogo;
import br.edu.ifrs.model.Plataforma;
import br.edu.ifrs.model.TipoPlataforma;
import br.edu.ifrs.persistence.JogoDao;
import br.edu.ifrs.persistence.PlataformaDao;
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

public class PlataformaDaoMockTest {
    private EntityManager em;
    private EntityTransaction et;
    PlataformaDao pdao;
    Plataforma p;

    @BeforeEach
    public void init() {
        p = p = new Plataforma("PlayStation", TipoPlataforma.CONSOLE, "Sony", "sony@gmail.com");
        em = mock(EntityManager.class);
        et = mock(EntityTransaction.class);
        when(em.getTransaction()).thenReturn(et);
        pdao = new PlataformaDao(em);
    }

    @Test
    @DisplayName("teste de insert com EM mockado")
    public void testInsert() {
        pdao.insert(p);
        verify(et).begin();
        verify(em).persist(p);
        verify(et).commit();
    }

    @Test
    @DisplayName("teste de delete com EM mockado")
    public void testDelete() {
        when(em.find(Plataforma.class, 2)).thenReturn(p);
        p.setId(2);
        pdao.delete(2);
        verify(et).begin();
        verify(em).remove(p);
        verify(et).commit();
    }

    @Test
    @DisplayName("teste de throw da função Delete")
    public void testDeleteThrow() {
        when(em.find(Plataforma.class, 99)).thenReturn(null);
        IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            pdao.delete(99);
        });
        Assertions.assertEquals("Não foi encontrada plataforma com ID 99", thrown.getMessage());
        verify(et, never()).begin();
        verify(em, never()).remove(any());
        verify(et, never()).commit();
    }

    @Test
    @DisplayName("teste de update com EM mockado")
    public void testUpdate (){
        pdao.update(p);
        verify(et).begin();
        verify(em).merge(p);
        verify(et).commit();
    }

    @Test
    @DisplayName("teste de find com EM mockado")
    public void testFind(){
        when(em.find(Plataforma.class, 2)).thenReturn(p);
        Plataforma novo = pdao.find(2);
        Assertions.assertEquals(p, novo);
    }

}
