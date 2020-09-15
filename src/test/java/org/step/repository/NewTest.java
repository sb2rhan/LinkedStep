package org.step.repository;

import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.step.entity.Profile;
import org.step.entity.User;

import java.util.List;

public class NewTest {

    @Before
    public void setup() {
        Session session = SessionFactoryCreator.getSession();

        session.getTransaction().begin();

        User user = User.builder()
                .username("first")
                .password("first")
                .age(18)
                .build();

        Profile profile = Profile.builder()
                .abilities("abilities")
                .graduation("grad")
                .workExperience("work")
                .fullName("full")
                .build();

        session.persist(user);
        session.persist(profile);

        session.getTransaction().commit();

        session.close();

        Session after = SessionFactoryCreator.getSession();

        after.getTransaction().begin();

        User userFromDb = after.createQuery("from User u", User.class).getResultList().get(0);
        Profile profileFromDb = after.createQuery("from Profile p", Profile.class).getResultList().get(0);

        userFromDb.setProfile(profileFromDb);
        profileFromDb.setUser(userFromDb);

        after.flush();

        after.getTransaction().commit();

        session.close();
    }

    @After
    public void clean() {
        Session session = SessionFactoryCreator.getSession();

        session.getTransaction().begin();

        User user = session.createQuery("from User u", User.class).getResultList().get(0);

        session.createQuery("delete from Profile p where p.user.id=:id")
                .setParameter("id", user.getId())
                .executeUpdate();

        session.createQuery("delete from User u where u.id=:id")
                .setParameter("id", user.getId())
                .executeUpdate();

        session.getTransaction().commit();

        session.close();
    }

    @Test
    public void test() {
        System.out.println("____________START______________");

        Session session = SessionFactoryCreator.getSession();

        session.getTransaction().begin();

        List<User> users = session.createQuery("select u from User u", User.class)
                .getResultList();

        session.getTransaction().commit();

        session.close();

        System.out.println("____________FINISH______________");

    }
}
