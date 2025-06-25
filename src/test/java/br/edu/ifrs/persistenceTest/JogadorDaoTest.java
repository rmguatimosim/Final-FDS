package br.edu.ifrs.persistenceTest;

import br.edu.ifrs.connectionFactory.ConnectionBD;
import br.edu.ifrs.model.Jogador;
import br.edu.ifrs.persistence.JogadorDao;

import org.hibernate.PropertyValueException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

public class JogadorDaoTest {
    JogadorDao jdao;
    Jogador j;

    //inicializa o JogadorDao no banco de testes
    @BeforeEach
    public void init(){
        jdao = new JogadorDao(ConnectionBD.connection("testUnit"));
        j = new Jogador("Rafael", "rafael@gmail.com", "01658023080", LocalDate.of(1990,7,6));
    }

    @Test
    @DisplayName("teste insert")
    public void testInsert(){
        jdao.insert(j);
        Assertions.assertNotEquals(0, j.getId());
    }

    @Test
    @DisplayName("Teste insert com usuário inválido")
    public void testInsertError(){
        Assertions.assertThrows(PropertyValueException.class, ()-> jdao.insert(new Jogador()));
    }

    @Test
    @DisplayName("teste find")
    public void testFind(){
        jdao.insert(j);
        Jogador novo = jdao.find(j.getId());
        Assertions.assertNotNull(novo);
    }
    @Test
    @DisplayName("teste find sem resultado")
    public void testFindNotFound(){
        jdao.insert(j);
        Jogador novo = jdao.find(50);
        Assertions.assertNull(novo);
    }

    @Test
    @DisplayName("teste update")
    public void testUpdate(){
        jdao.insert(j);
        Jogador novo = jdao.find(j.getId());
        novo.setNome("Tobias");
        jdao.update(novo);
        Assertions.assertEquals("Tobias", jdao.find(j.getId()).getNome());
    }
    @Test
    @DisplayName("teste update user inválido")
    public void testUpdateInvalidUser(){
        Jogador novo  = new Jogador();
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
        Jogador j2 = new Jogador("Rafael Novo", "rafael@gmail.com1", "52723763072", LocalDate.of(1990,7,6));
        Jogador j3 = new Jogador("Rafael de Novo", "rafael@gmail.com13", "02506592040", LocalDate.of(1990,7,6));
        jdao.insert(j);
        jdao.insert(j2);
        jdao.insert(j3);
        List<Jogador> lista = jdao.findAll(0,100);
        Assertions.assertEquals(3, lista.size());
    }

}
