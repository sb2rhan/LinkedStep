package org.step.repository;

import org.hibernate.Session;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.step.entity.Profile;
import org.step.entity.User;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.stream.Collectors;

public class UserRepositoryTest {

    private final UserRepository userRepository = new UserRepository();
    private List<Long> userIdList;

    @Before
    public void setup() {
        User first = User.builder()
                .id(null)
                .username("first")
                .build();

        User second = User.builder()
                .id(null)
                .username("second")
                .build();

        Session session = SessionFactoryCreator.getSession();

        session.getTransaction().begin();

        session.persist(first);
        session.persist(second);

        session.getTransaction().commit();

        session.close();

        Session secondSession = SessionFactoryCreator.getSession();

        secondSession.getTransaction().begin();

        userIdList = secondSession.createQuery("select u from User u", User.class)
                .getResultList()
                .stream()
                .map(User::getId)
                .collect(Collectors.toList());

        Profile firstProfile = Profile.builder()
                .abilities("good")
                .graduation("good")
                .workExperience("good")
                .fullName("Iron man")
                .build();

        Profile secondProfile = Profile.builder()
                .abilities("poor")
                .graduation("poor")
                .workExperience("poor")
                .fullName("Hulk")
                .build();

        secondSession.persist(firstProfile);
        secondSession.persist(secondProfile);

        secondSession.getTransaction().commit();

        secondSession.close();

        Session thirdSession = SessionFactoryCreator.getSession();

        thirdSession.getTransaction().begin();

        User afterSaving = thirdSession.find(User.class, userIdList.get(0));
        User secondAfterSaving = thirdSession.find(User.class, userIdList.get(1));

        List<Profile> profileList = thirdSession.createQuery("select p from Profile p", Profile.class)
                .getResultList();

        profileList.get(0).setUser(afterSaving);
        afterSaving.setProfile(profileList.get(0));

        profileList.get(1).setUser(secondAfterSaving);
        secondAfterSaving.setProfile(profileList.get(1));

        thirdSession.getTransaction().commit();

        thirdSession.close();
    }

    @After
    public void clean() {
        Session session = SessionFactoryCreator.getSession();

        session.getTransaction().begin();

        session.createQuery("delete from Profile p")
                .executeUpdate();

        session.createQuery("delete from User u")
                .executeUpdate();

        session.getTransaction().commit();

        session.close();
    }

    @Test
    public void saveUserTest() {
        final String username = "second";

        User user = User.builder()
                .username(username)
                .build();

        userRepository.saveUser(user);

        Session session = SessionFactoryCreator.getSession();

        Long id = session.createQuery("select u from User u", User.class)
                .getResultList()
                .stream()
                .map(User::getId)
                .max(Long::compareTo)
                .orElse(null);

        User userFromDB = session.find(User.class, id);

        session.close();

        Assert.assertNotNull(userFromDB); // not null
        Assert.assertEquals(id, user.getId()); // id equals
        Assert.assertEquals(username, user.getUsername()); // username equals
    }

    @Test
    public void updateUsernameTest() {
        final String newUsername = "new username";

        userRepository.updateUsername(newUsername, userIdList.get(0));

        Session session = SessionFactoryCreator.getSession();

        User user = session.find(User.class, userIdList.get(0));

        session.close();

        Assert.assertNotNull(user);
        Assert.assertEquals(userIdList.get(0), user.getId());
        Assert.assertEquals(newUsername, user.getUsername());
    }

    @Test
    public void findAllTest() {
        List<User> allUsers = userRepository.findAll();

        Assert.assertNotNull(allUsers);
        Assert.assertFalse(allUsers.isEmpty());
        Assert.assertTrue(allUsers.contains(User.builder().id(userIdList.get(0)).build()));
    }
}
