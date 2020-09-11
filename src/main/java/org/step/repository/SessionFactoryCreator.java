package org.step.repository;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.step.entity.User;

public class SessionFactoryCreator {

    public static SessionFactory sessionFactoryBuilder() {
        String fileNameInResources = "hibernate.cfg.xml"; // name of configuration file in resources

        return new Configuration() // create configuration
                .configure(fileNameInResources) // add configuration file name
                .addAnnotatedClass(User.class) // add annotated class
                .buildSessionFactory(); // create session factory
    }
}
