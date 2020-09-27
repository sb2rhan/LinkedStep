package org.step.repository;

import org.hibernate.Session;
import org.hibernate.graph.RootGraph;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.step.entity.Group;
import org.step.entity.Profile;
import org.step.entity.View;
import org.step.repository.impl.ProfileRepositoryImpl;

import javax.persistence.EntityManager;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import java.math.BigInteger;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ProfileRepositoryTest {

    private final ProfileRepository profileRepository = new ProfileRepositoryImpl();
    private final EntityManager entityManager = SessionFactoryCreator.getEntityManager();
    private final List<Profile> profiles = new ArrayList<>();

    @Before
    public void setup() {
        System.out.println("----------------------------SETUP----------------------------");
        Profile profile = Profile.builder()
                .fullName("Markus Bat")
                .abilities("writes only clean code")
                .graduation("Software engineer")
                .workExperience("2 years")
                .build();

        View view = View.builder()
                .date(Date.valueOf("2020-01-23"))
                .build();

        profile.addView(view);

        entityManager.getTransaction().begin();

        entityManager.persist(profile);

        entityManager.getTransaction().commit();

        profiles.add(profile);
        System.out.println("----------------------------SETUP----------------------------");
    }

    @After
    public void clean() {
        System.out.println("----------------------------CLEAN----------------------------");
        entityManager.getTransaction().begin();

        entityManager.createQuery("delete from View v").executeUpdate();

        entityManager.createQuery("delete from Profile p").executeUpdate();

        entityManager.getTransaction().commit();
        System.out.println("----------------------------CLEAN----------------------------");
    }

    @Test
    public void testFindAll() {
        List<Profile> allProfiles = profileRepository.findAll();
        allProfiles.forEach(profile -> System.out.println(profile.getViews().get(0).toString()));

        Assert.assertNotNull(allProfiles);
        Assert.assertFalse(allProfiles.isEmpty());
        Assert.assertTrue(allProfiles.contains(Profile.builder().id(profiles.get(0).getId()).build()));
    }

    @Test
    public void testFindById() {
        final int chosenIndex = 0;
        final Long testId = profiles.get(chosenIndex).getId();
        Profile profile = profileRepository.findById(testId);

        Assert.assertNotNull(profile);
        Assert.assertNotNull(profile.getViews());
        Assert.assertEquals(profile, profiles.get(chosenIndex));
    }

    @Test
    public void testFindByFullName() {
        final int chosenIndex = 0;
        final String testFullName = profiles.get(chosenIndex).getFullName();
        List<Profile> profileList = profileRepository.findByFullName(testFullName);
        profileList.forEach(System.out::println);
        profileList.forEach(p -> System.out.println(p.getViews().get(0).toString()));

        Assert.assertNotNull(profileList);
        Assert.assertFalse(profileList.isEmpty());
        Assert.assertTrue(profileList.contains(profiles.get(chosenIndex)));
    }

    @Test
    public void testDeleteProfile() {
        final int chosenIndex = 0;
        final Long testId = profiles.get(chosenIndex).getId();
        profileRepository.delete(testId);

        BigInteger profileSingleResult = (BigInteger) entityManager
                .createNativeQuery("SELECT count(1) FROM PROFILES WHERE USER_ID=:id")
                .setParameter("id", testId)
                .getSingleResult();

        Assert.assertEquals(chosenIndex, profileSingleResult.intValue());
    }

    @Test
    public void testAddGroupToProfile() {
        Group group = Group.builder()
                .id()
                .groupName("Daily News")
                .description("You can see some news there")
                .category("Informational")
                .build();
        Profile exampleProfile = profileRepository.findAll().get(0);
        entityManager.getTransaction().begin();

        entityManager.persist(group);

        entityManager.flush();
        exampleProfile.addGroup(group);

        entityManager.getTransaction().commit();
    }

    @Test
    public void testGetGroupsOfProfile() {
        /*Создаем ManyToMany всеми тремя способами, проявите творчество, поработайте
        с графами и попробуйте поработать с Native SQL.*/
        Profile chosenOne = profiles.get(0);

        Group group = Group.builder()
                .id()
                .groupName("Cool guys")
                .description("Only for cool people")
                .category("Chatting")
                .build();

        entityManager.getTransaction().begin();

        entityManager.persist(group);
        entityManager.flush();
        chosenOne.addGroup(group);
        entityManager.flush();

        // ------ getting through RootGraph -----
        Session session = SessionFactoryCreator.getSession();

        RootGraph<Profile> graph = session.createEntityGraph(Profile.class);
        graph.addAttributeNodes("groupSet");

        Profile singleProfile = session.createQuery("select p from Profile p where p.id=:id", Profile.class)
                .setParameter("id", chosenOne.getId())
                .applyFetchGraph(graph)
                .getSingleResult();

        session.close();

        System.out.println(singleProfile);
        // -------------------------------------

        // ---- getting from profile_group table ----
        List<Object> groupsFrom3rdTable = entityManager.createNativeQuery("SELECT group_id FROM profile_group WHERE profile_id=?")
                .setParameter(1, chosenOne.getId())
                .getResultList();

        System.out.println(groupsFrom3rdTable.toString());

        List<Group> groups = entityManager.createQuery("select g from Group g where g.id in :id", Group.class)
                .setParameter("id", groupsFrom3rdTable)
                .getResultList();

        System.out.println(groups.toString());
        // -----------------------------------------

        entityManager.getTransaction().commit();
    }

    @Test
    // Тест без исключения
    public void testGroupValidatorPassed() {
        Profile chosenOne = profiles.get(0);

        Group group = Group.builder()
                .id()
                .groupName("CodeHub")
                .description("Share your projects here")
                .category("Coding")
                .build();

        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<Group>> violations = validator.validate(group);
        if (!violations.isEmpty()) {
            System.out.println(violations.toString());
            throw new ConstraintViolationException(violations);
        }

        entityManager.getTransaction().begin();

        entityManager.persist(group);
        entityManager.flush();

        chosenOne.addGroup(group);
        entityManager.flush();

        entityManager.getTransaction().commit();
    }

    @Test
    // Тест с исключением
    public void testGroupValidatorWithException() {
        Profile chosenOne = profiles.get(0);

        Group group = Group.builder()
                .id()
                .groupName("Tiktok")
                .description("This group is not going to pass")
                .category("Social media")
                .build();

        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<Group>> violations = validator.validate(group);

        if (!violations.isEmpty()) {
            System.out.println(violations.toString());
            throw new ConstraintViolationException(violations);
        }
        entityManager.getTransaction().begin();

        entityManager.persist(group);
        entityManager.flush();

        chosenOne.addGroup(group);
        entityManager.flush();

        entityManager.getTransaction().commit();

    }
}
