package br.edu.ifrs.persistence;

import br.edu.ifrs.connectionFactory.ConnectionBD;
import br.edu.ifrs.model.Jogo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Query;

import java.util.List;

public class JogoDao implements dao<Jogo>{

    private EntityManager manager;

    public JogoDao(EntityManager em){
        this.manager = em;
    }

    @Override
    public void insert(Jogo obj) {
        manager.getTransaction().begin();
        manager.persist(obj);
        manager.getTransaction().commit();
    }

    @Override
    public void update(Jogo obj) {
        manager.getTransaction().begin();
        manager.merge(obj);
        manager.getTransaction().commit();
    }

    @Override
    public void delete(int id) {
        Jogo j = manager.find(Jogo.class, id);
        if(j != null){
            manager.getTransaction().begin();
            manager.remove(j);
            manager.getTransaction().commit();
        }
        else{
            throw new IllegalArgumentException("Não foi encontrado jogo com ID " + id);
        }

    }

    @Override
    public Jogo find(int id) {
        return manager.find(Jogo.class, id);
    }

    @Override
    public List<Jogo> findAll(int offset, int limit) {
        Query sql = manager.createQuery("SELECT j FROM Jogo j ORDER BY j.titulo", Jogo.class);
        sql.setFirstResult(offset).setMaxResults(limit);

        List<Jogo> lista = sql.getResultList();
        return lista;
    }
}
