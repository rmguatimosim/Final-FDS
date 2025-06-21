package br.edu.ifrs.persistence;

import br.edu.ifrs.model.Jogador;
import br.edu.ifrs.model.Plataforma;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

import java.util.List;

public class PlataformaDao extends daoJPA implements dao<Plataforma>{

    private EntityManager manager;
    PlataformaDao(){
        this.manager = emFac.createEntityManager();
    }

    @Override
    public void insert(Plataforma obj) {
        manager.getTransaction().begin();
        manager.persist(obj);
        manager.getTransaction().commit();
    }

    @Override
    public void update(Plataforma obj) {
        manager.getTransaction().begin();
        manager.merge(obj);
        manager.getTransaction().commit();
    }

    @Override
    public void delete(int id) {
        manager.getTransaction().begin();
        manager.remove(manager.getReference(Plataforma.class, id));
        manager.getTransaction().commit();
    }

    @Override
    public Plataforma find(int id) {
        return manager.find(Plataforma.class, id);
    }

    @Override
    public List<Plataforma> findAll(int offset, int limit) {
        Query sql = manager.createQuery("SELECT p FROM Plataforma p", Plataforma.class);
        sql.setFirstResult(offset).setMaxResults(limit);

        List<Plataforma> lista = sql.getResultList();
        return lista;
    }
}
