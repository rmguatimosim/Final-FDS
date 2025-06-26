package br.edu.ifrs.persistence;

import br.edu.ifrs.connectionFactory.ConnectionBD;
import br.edu.ifrs.model.Jogador;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Query;

import java.util.List;

public class JogadorDao implements dao<Jogador>{

    private final EntityManager manager;

    public JogadorDao(EntityManager em){
        this.manager = em;
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
        Jogador j = manager.find(Jogador.class, id);
        if(j != null){
            this.manager.getTransaction().begin();
            manager.remove(j);
            this.manager.getTransaction().commit();
        }
        else{
            throw new IllegalArgumentException("Não foi encontrado jogador com ID " + id);
        }
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
