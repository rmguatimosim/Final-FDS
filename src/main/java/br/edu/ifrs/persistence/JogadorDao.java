package br.edu.ifrs.persistence;

import br.edu.ifrs.model.Jogador;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import java.util.List;

public class JogadorDao implements dao<Jogador>{

    private final EntityManager manager;

    public JogadorDao(EntityManager em){
        this.manager = em;
    }

    @Override
    public void insert(Jogador obj) {
        manager.getTransaction().begin();
        manager.persist(obj);
        manager.getTransaction().commit();
    }

    @Override
    public void update(Jogador obj) {
        manager.getTransaction().begin();
        manager.merge(obj);
        manager.flush();
        manager.getTransaction().commit();
    }

    @Override
    public void delete(int id) {
        Jogador j = manager.find(Jogador.class, id);
        if(j != null){
            manager.getTransaction().begin();
            manager.remove(j);
            manager.getTransaction().commit();
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
        Query sql = manager.createQuery("SELECT j FROM Jogador j ORDER BY j.id", Jogador.class);
        sql.setFirstResult(offset).setMaxResults(limit);

        List<Jogador> lista = sql.getResultList();
        return lista;
    }

    public List<Jogador> findByPlataforma(int plataformaId){
        return manager.createQuery(
                        "SELECT j FROM Jogador j WHERE j.plataforma.id = :plataformaId", Jogador.class)
                .setParameter("plataformaId", plataformaId)
                .getResultList();
    }



}
