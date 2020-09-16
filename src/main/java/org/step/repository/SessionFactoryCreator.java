package org.step.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.step.entity.Profile;
import org.step.entity.User;

import javax.persistence.EntityManager;

public class SessionFactoryCreator {

    private static final String FILE_NAME_IN_RESOURCES = "hibernate.cfg.xml"; // name of configuration file in resources

    private static final SessionFactory SESSION_FACTORY = new Configuration() // create configuration
                .configure(FILE_NAME_IN_RESOURCES) // add configuration file name
                .addAnnotatedClass(User.class) // add annotated class
                .addAnnotatedClass(Profile.class)
                .buildSessionFactory();

    private static final EntityManager ENTITY_MANAGER = SESSION_FACTORY.createEntityManager();

    public static Session getSession() {
        return SESSION_FACTORY.openSession();
    }

    public static EntityManager getEntityManager() {
        return ENTITY_MANAGER;
    }
}
