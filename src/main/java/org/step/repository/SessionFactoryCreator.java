package org.step.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.step.entity.User;

public class SessionFactoryCreator {

    private static final String FILE_NAME_IN_RESOURCES = "hibernate.cfg.xml"; // name of configuration file in resources

    private static final SessionFactory SESSION_FACTORY = new Configuration() // create configuration
                .configure(FILE_NAME_IN_RESOURCES) // add configuration file name
                .addAnnotatedClass(User.class) // add annotated class
                .buildSessionFactory();

    public static Session getSession() {
        return SESSION_FACTORY.openSession();
    }
}
