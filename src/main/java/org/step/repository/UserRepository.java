package org.step.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.step.entity.User;

import java.util.Optional;

public class UserRepository {

    public void updateUsername(String username, Long id) {
        SessionFactory sessionFactory = SessionFactoryCreator.sessionFactoryBuilder();

        Session session = sessionFactory.openSession();

        session.getTransaction().begin();

        User user = session.find(User.class, id);

        user.setUsername(username);

        session.getTransaction().commit();

        session.close();
    }

    public void saveUser(User user) {
        SessionFactory sessionFactory = SessionFactoryCreator.sessionFactoryBuilder();

        Session session = sessionFactory.openSession();

        session.getTransaction().begin();

        session.persist(user);

        session.getTransaction().commit();

        session.close();
    }
}
