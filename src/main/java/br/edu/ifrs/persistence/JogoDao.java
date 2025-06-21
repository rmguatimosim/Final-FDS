package br.edu.ifrs.persistence;

import br.edu.ifrs.model.Jogador;
import br.edu.ifrs.model.Jogo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

import java.util.List;

public class JogoDao extends daoJPA implements dao<Jogo>{

    private EntityManager manager;

    JogoDao(){
        this.manager = emFac.createEntityManager();
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
        manager.getTransaction().begin();
        manager.remove(manager.getReference(Jogo.class, id));
        manager.getTransaction().commit();
    }

    @Override
    public Jogo find(int id) {
        return manager.find(Jogo.class, id);
    }

    @Override
    public List<Jogo> findAll(int offset, int limit) {
        Query sql = manager.createQuery("SELECT j FROM Jogo j", Jogo.class);
        sql.setFirstResult(offset).setMaxResults(limit);

        List<Jogo> lista = sql.getResultList();
        return lista;
    }
}
