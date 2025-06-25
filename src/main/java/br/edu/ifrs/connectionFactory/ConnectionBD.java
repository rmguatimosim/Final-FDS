package br.edu.ifrs.connectionFactory;


import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class ConnectionBD {

    public static EntityManagerFactory connection(String unit){
        EntityManagerFactory enfac = Persistence.createEntityManagerFactory(unit);
        return enfac;
    }
}
