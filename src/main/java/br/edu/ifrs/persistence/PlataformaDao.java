package br.edu.ifrs.persistence;

import br.edu.ifrs.connectionFactory.ConnectionBD;
import br.edu.ifrs.model.Plataforma;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Query;

import java.util.List;

public class PlataformaDao implements dao<Plataforma>{

    private final EntityManager manager;
    public PlataformaDao(EntityManager em){
        this.manager = em;
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
        Plataforma p = manager.find(Plataforma.class, id);
        if(p!=null){
            manager.getTransaction().begin();
            manager.remove(p);
            manager.getTransaction().commit();
        }
        else{
            throw new IllegalArgumentException("Não foi encontrada plataforma com ID " + id);
        }

    }

    @Override
    public Plataforma find(int id) {
        return manager.find(Plataforma.class, id);
    }

    @Override
    public List<Plataforma> findAll(int offset, int limit) {
        Query sql = manager.createQuery("SELECT p FROM Plataforma p ORDER BY p.id", Plataforma.class);
        sql.setFirstResult(offset).setMaxResults(limit);

        List<Plataforma> lista = sql.getResultList();
        return lista;
    }
}
