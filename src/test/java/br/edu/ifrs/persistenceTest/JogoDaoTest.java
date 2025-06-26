package br.edu.ifrs.persistenceTest;

import br.edu.ifrs.connectionFactory.ConnectionBD;
import br.edu.ifrs.model.Jogador;
import br.edu.ifrs.model.Jogo;
import br.edu.ifrs.persistence.JogoDao;
import jakarta.persistence.EntityManager;
import org.hibernate.PropertyValueException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

public class JogoDaoTest {
    EntityManager em = ConnectionBD.connection("testUnit");
    JogoDao jdao;
    Jogo j;

    @BeforeEach
    public void init(){
        jdao = new JogoDao(em);
        j = new Jogo("Elden Ring", 2022, "From Software", "Bandai Namco");
    }

    @Test
    @DisplayName("teste insert")
    public void testInsert(){
        jdao.insert(j);
        Assertions.assertNotEquals(0, j.getId());
    }

    @Test
    @DisplayName("Teste insert com jogo inválido")
    public void testInsertError(){
        Assertions.assertThrows(PropertyValueException.class, ()-> jdao.insert(new Jogo()));
    }

    @Test
    @DisplayName("teste find")
    public void testFind(){
        jdao.insert(j);
        Jogo novo = jdao.find(j.getId());
        Assertions.assertNotNull(novo);
    }
    @Test
    @DisplayName("teste find sem resultado")
    public void testFindNotFound(){
        jdao.insert(j);
        Jogo novo = jdao.find(9998);
        Assertions.assertNull(novo);
    }

    @Test
    @DisplayName("teste update")
    public void testUpdate(){
        jdao.insert(j);
        Jogo novo = jdao.find(j.getId());
        novo.setTitulo("Armored Core");
        jdao.update(novo);
        Assertions.assertEquals("Armored Core", jdao.find(j.getId()).getTitulo());
    }
    @Test
    @DisplayName("teste update jogo inválido")
    public void testUpdateInvalidUser(){
        Jogo novo  = new Jogo();
        novo.setId(j.getId());
        Assertions.assertThrows(PropertyValueException.class, ()-> jdao.update(novo));
    }

    @Test
    @DisplayName("teste delete")
    public void testDelete(){
        jdao.insert(j);
        jdao.delete(j.getId());
        Assertions.assertNull(jdao.find(j.getId()));
    }
    @Test
    @DisplayName("teste delete usuário não encontrado")
    public void testDeleteNoUser(){
        Assertions.assertThrows(IllegalArgumentException.class, ()->jdao.delete(99999));
    }

    @Test
    @DisplayName("teste findAll")
    public void testFindAll(){
        Jogo j2 = new Jogo("Jogo dois", 2022, "From Software", "Bandai Namco");
        Jogo j3 = new Jogo("Jogo três", 2022, "From Software", "Bandai Namco");
        jdao.insert(j);
        jdao.insert(j2);
        jdao.insert(j3);
        List<Jogo> lista = jdao.findAll(0,100);
        Assertions.assertEquals(3, lista.size());
    }


}
