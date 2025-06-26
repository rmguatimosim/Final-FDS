package br.edu.ifrs.persistenceTest;

import br.edu.ifrs.connectionFactory.ConnectionBD;
import br.edu.ifrs.model.Plataforma;
import br.edu.ifrs.model.TipoPlataforma;
import br.edu.ifrs.persistence.PlataformaDao;
import jakarta.persistence.EntityManager;
import org.hibernate.PropertyValueException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

public class PlataformaDaoTest {
    EntityManager em = ConnectionBD.connection("testUnit");
    PlataformaDao pdao;
    Plataforma p;

    @BeforeEach
    public void init(){
        pdao = new PlataformaDao(em);
        p = new Plataforma("PlayStation", TipoPlataforma.CONSOLE, "Sony", "sony@gmail.com");
    }

    @Test
    @DisplayName("teste insert")
    public void testInsert(){
        pdao.insert(p);
        Assertions.assertNotEquals(0, p.getId());
    }

    @Test
    @DisplayName("Teste insert com plataforma inválida")
    public void testInsertError(){
        Assertions.assertThrows(PropertyValueException.class, ()-> pdao.insert(new Plataforma()));
    }

    @Test
    @DisplayName("teste find")
    public void testFind(){
        pdao.insert(p);
        Plataforma novo = pdao.find(p.getId());
        Assertions.assertNotNull(novo);
    }
    @Test
    @DisplayName("teste find sem resultado")
    public void testFindNotFound(){
        pdao.insert(p);
        Plataforma novo = pdao.find(9999);
        Assertions.assertNull(novo);
    }

    @Test
    @DisplayName("teste update")
    public void testUpdate(){
        pdao.insert(p);
        Plataforma novo = pdao.find(p.getId());
        novo.setNome("Xbox");
        pdao.update(novo);
        Assertions.assertEquals("Xbox", pdao.find(p.getId()).getNome());
    }
    @Test
    @DisplayName("teste update plataforma inválida")
    public void testUpdateInvalidUser(){
        Plataforma novo  = new Plataforma();
        novo.setId(p.getId());
        Assertions.assertThrows(PropertyValueException.class, ()-> pdao.update(novo));
    }

    @Test
    @DisplayName("teste delete")
    public void testDelete(){
        pdao.insert(p);
        pdao.delete(p.getId());
        Assertions.assertNull(pdao.find(p.getId()));
    }
    @Test
    @DisplayName("teste delete usuário não encontrado")
    public void testDeleteNoUser(){
        Assertions.assertThrows(IllegalArgumentException.class, ()->pdao.delete(99999));
    }

    @Test
    @DisplayName("teste findAll")
    public void testFindAll(){
        Plataforma p2 = new Plataforma("Xbox", TipoPlataforma.CONSOLE, "Microsoft", "xbox@gmail.com");
        Plataforma p3 = new Plataforma("Dreamcast", TipoPlataforma.CONSOLE, "Sega", "sega@gmail.com");
        pdao.insert(p);
        pdao.insert(p2);
        pdao.insert(p3);
        List<Plataforma> lista = pdao.findAll(0,100);
        Assertions.assertEquals(3, lista.size());
    }

}
