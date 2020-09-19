package org.step.repository;

import org.hibernate.Session;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.step.entity.Profile;
import org.step.entity.User;
import org.step.repository.impl.UserRepositoryImpl;

import javax.persistence.EntityManager;
import javax.persistence.FlushModeType;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class UserRepositoryImplTest {

    private final UserRepository userRepositoryImpl = new UserRepositoryImpl();
    private final List<Long> userIdList = new ArrayList<>();

    @Before
    public void setup() {
        System.out.println("******************SETUP*******************");

        EntityManager entityManager = SessionFactoryCreator.getEntityManager();

        entityManager.getTransaction().begin();

        for (int i = 0; i < 10; i++) {
            User first = User.builder()
                    .username("first " + i)
                    .build();

            Profile profile = Profile.builder()
                    .fullName("first full name " + i)
                    .graduation("KBTU")
                    .abilities("good speaking")
                    .workExperience("student")
                    .build();

            first.addProfile(profile);

            entityManager.persist(first);

//        session.flush();

        /*
        flush - сохранение в БД

        1. Вызываем на прямую flush()
        2. При вызове commit()

        Можно настроить
        1. Перед каждой Query
        2. AUTO - переодически флашит изменения
         */
            entityManager.flush();

            userIdList.add(first.getId());

        }
        entityManager.getTransaction().commit();

        System.out.println("******************SETUP*******************");
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

        User savedUser = userRepositoryImpl.save(user);

        Session session = SessionFactoryCreator.getSession();

        User userFromDB = session.find(User.class, savedUser.getId());

        session.close();

        Assert.assertNotNull(userFromDB); // not null
        Assert.assertEquals(userFromDB.getId(), savedUser.getId()); // id equals
        Assert.assertEquals(username, user.getUsername()); // username equals
    }

    @Test
    public void updateUsernameTest() {
        final String newUsername = "new username";

        userRepositoryImpl.updateUsername(newUsername, userIdList.get(0));

        Session session = SessionFactoryCreator.getSession();

        User user = session.find(User.class, userIdList.get(0));

        session.close();

        Assert.assertNotNull(user);
        Assert.assertEquals(userIdList.get(0), user.getId());
        Assert.assertEquals(newUsername, user.getUsername());
    }

    @Test
    public void findAllTest() {
        List<User> allUsers = userRepositoryImpl.findAll();

        Assert.assertNotNull(allUsers);
        Assert.assertFalse(allUsers.isEmpty());
        Assert.assertTrue(allUsers.contains(User.builder().id(userIdList.get(0)).build()));
    }

    @Test
    public void deleteUser() {
        final int zero = 0;
        final Long id = userIdList.get(zero);

        userRepositoryImpl.delete(id);

        Session session = SessionFactoryCreator.getSession();

        BigInteger userSingleResult = (BigInteger) session.createNativeQuery("SELECT count(1) FROM USERS WHERE ID=:id")
                .setParameter("id", id)
                .getSingleResult();

        BigInteger profileSingleResult = (BigInteger) session.createNativeQuery("SELECT count(1) FROM PROFILES WHERE USER_ID=:id")
                .setParameter("id", id)
                .getSingleResult();

        Assert.assertEquals(zero, userSingleResult.intValue());
        Assert.assertEquals(zero, profileSingleResult.intValue());
    }
}
