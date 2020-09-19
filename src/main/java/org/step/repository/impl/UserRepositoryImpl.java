package org.step.repository.impl;

import org.hibernate.Session;
import org.step.entity.User;
import org.step.repository.SessionFactoryCreator;
import org.step.repository.UserRepository;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserRepositoryImpl implements UserRepository {

    @Override
    public void updateUsername(String username, Long id) {
        Session session = SessionFactoryCreator.getSession();

        session.getTransaction().begin();

        User user = session.find(User.class, id);

        user.setUsername(username);

        session.getTransaction().commit();

        session.close();
    }

    @Override
    public User save(User user) {
        Session session = SessionFactoryCreator.getSession();

        session.getTransaction().begin();

        session.persist(user);

        session.getTransaction().commit();

        session.close();

        return user;
    }

    @Override
    public List<User> findAll() {
        Session session = SessionFactoryCreator.getSession();

//        EntityGraph<User> graph = session.createEntityGraph(User.class);
//
//        graph.addAttributeNodes("profile");

        List<User> users = new ArrayList<>();

        session.doWork(connection -> {
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT * FROM USERS");

            while (resultSet.next()) {
                users.add(
                        User.builder()
                        .id(resultSet.getLong("id"))
                        .username(resultSet.getString("username"))
                        .password(resultSet.getString("password"))
                        .age(resultSet.getInt("age"))
                        .build()
                );
            }
        });

//        List<User> users = session.createQuery("select u from User u", User.class)
//                .setHint("javax.persistence.fetchgraph", graph)
//                .getResultList();

        session.close();

        return users;
    }

    @Override
    public void delete(Long id) {
        Session session = SessionFactoryCreator.getSession();

        session.beginTransaction();

        session.createQuery("delete from Profile p where p.user.id=:id")
                .setParameter("id", id)
                .executeUpdate();

        session.createQuery("delete from User u where u.id=:id")
                .setParameter("id", id)
                .executeUpdate();

        session.getTransaction().commit();

        session.close();
    }
}
