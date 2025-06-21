package br.edu.ifrs.persistence;

import br.edu.ifrs.model.Jogador;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

import java.util.List;

public class JogadorDao extends daoJPA implements dao<Jogador>{

    private EntityManager manager;

    JogadorDao(){
        this.manager = emFac.createEntityManager();
    }

    @Override
    public void insert(Jogador obj) {
        this.manager.getTransaction().begin();
        manager.persist(obj);
        this.manager.getTransaction().commit();
    }

    @Override
    public void update(Jogador obj) {
        this.manager.getTransaction().begin();
        manager.merge(obj);
        this.manager.getTransaction().commit();
    }

    @Override
    public void delete(int id) {
        this.manager.getTransaction().begin();
        manager.remove(manager.getReference(Jogador.class, id));
        this.manager.getTransaction().commit();
    }

    @Override
    public Jogador find(int id) {
        return manager.find(Jogador.class, id);
    }

    @Override
    public List<Jogador> findAll(int offset, int limit) {
        Query sql = manager.createQuery("SELECT j FROM Jogador j", Jogador.class);
        sql.setFirstResult(offset).setMaxResults(limit);

        List<Jogador> lista = sql.getResultList();
        return lista;
    }
}
