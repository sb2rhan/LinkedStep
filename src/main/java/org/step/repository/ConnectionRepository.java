package org.step.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.step.entity.User;

import java.util.Map;

public class ConnectionRepository {

    public Map<String, Object> getInformationFromDatabase() {
        SessionFactory sessionFactory = SessionFactoryCreator.sessionFactoryBuilder();

        Session session = sessionFactory
                .openSession();

        Map<String, Object> properties = session
                .getProperties();

        session.close();

        return properties;
    }
}
