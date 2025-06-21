package br.edu.ifrs.persistence;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public abstract class daoJPA {
    public static final EntityManagerFactory emFac = Persistence.createEntityManagerFactory ("PUmain");
}
