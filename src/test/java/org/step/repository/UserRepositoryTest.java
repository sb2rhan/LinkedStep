package org.step.repository;

import org.hibernate.Session;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.step.entity.User;

import java.util.List;

public class UserRepositoryTest {

    private final long id = 1L;
    private final long secondId = 2L;
    private final UserRepository userRepository = new UserRepository();

    @Before
    public void setup() {
        User first = User.builder()
                .id(id)
                .username("first")
                .build();

        User second = User.builder()
                .id(secondId)
                .username("second")
                .build();

        Session session = SessionFactoryCreator.getSession();

        session.getTransaction().begin();

        session.persist(first);
        session.persist(second);

        session.getTransaction().commit();

        session.close();
    }

    @After
    public void clean() {
        Session session = SessionFactoryCreator.getSession();

        session.getTransaction().begin();

        session.createQuery("delete from User u")
                .executeUpdate();

        session.getTransaction().commit();

        session.close();
    }

    @Test
    public void saveUserTest() {
        final long id = 3L;
        final String username = "second";

        User user = User.builder()
                .id(id)
                .username(username)
                .build();

        userRepository.saveUser(user);

        Session session = SessionFactoryCreator.getSession();

        User userFromDB = session.find(User.class, id);

        session.close();

        Assert.assertNotNull(userFromDB); // not null
        Assert.assertEquals(id, (long) user.getId()); // id equals
        Assert.assertEquals(username, user.getUsername()); // username equals
    }

    @Test
    public void updateUsernameTest() {
        final String newUsername = "new username";

        userRepository.updateUsername(newUsername, id);

        Session session = SessionFactoryCreator.getSession();

        User user = session.find(User.class, id);

        session.close();

        Assert.assertNotNull(user);
        Assert.assertEquals(id, (long) user.getId());
        Assert.assertEquals(newUsername, user.getUsername());
    }

    @Test
    public void findAllTest() {
        List<User> allUsers = userRepository.findAll();

        Assert.assertNotNull(allUsers);
        Assert.assertFalse(allUsers.isEmpty());
        Assert.assertTrue(allUsers.contains(User.builder().id(id).build()));
    }
}
