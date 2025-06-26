package br.edu.ifrs.connectionFactory;


import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class ConnectionBD {

    public static EntityManager connection(String unit){
        EntityManagerFactory enfac = Persistence.createEntityManagerFactory(unit);
        return enfac.createEntityManager();
    }
}
