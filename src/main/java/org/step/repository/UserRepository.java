package org.step.repository;

import org.hibernate.Session;
import org.step.entity.User;

public class UserRepository {

    public void updateUsername(String username, Long id) {
        Session session = SessionFactoryCreator.getSession();

        session.getTransaction().begin();

        User user = session.find(User.class, id);

        user.setUsername(username);

        session.getTransaction().commit();

        session.close();
    }

    public void saveUser(User user) {
        Session session = SessionFactoryCreator.getSession();

        session.getTransaction().begin();

        session.persist(user);

        session.getTransaction().commit();

        session.close();
    }
}
