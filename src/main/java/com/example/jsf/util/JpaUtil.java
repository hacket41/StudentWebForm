package com.example.jsf.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JpaUtil {

    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("studentPU");

    // Provide an EntityManager for use in DAOs
    public static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    // Optional: close EntityManagerFactory on app shutdown
    public static void close() {
        emf.close();
    }
}
