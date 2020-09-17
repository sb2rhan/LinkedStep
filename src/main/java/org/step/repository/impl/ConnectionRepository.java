package org.step.repository.impl;

import org.hibernate.Session;
import org.step.repository.SessionFactoryCreator;

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
