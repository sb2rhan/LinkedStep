package org.step.repository;

import org.hibernate.Session;

import java.util.Map;

public class ConnectionRepository {

    public Map<String, Object> getInformationFromDatabase() {
        Session session = SessionFactoryCreator.getSession();

        Map<String, Object> properties = session
                .getProperties();

        session.close();

        return properties;
    }
}
