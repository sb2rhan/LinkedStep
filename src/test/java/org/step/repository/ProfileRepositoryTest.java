package org.step.repository;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.step.entity.Profile;
import org.step.repository.impl.ProfileRepositoryImpl;

import javax.persistence.EntityManager;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class ProfileRepositoryTest {

    private final ProfileRepository profileRepository = new ProfileRepositoryImpl();
    private final EntityManager entityManager = SessionFactoryCreator.getEntityManager();
    private final List<Profile> profiles = new ArrayList<>();

    @Before
    public void setup() {
        Profile profile = Profile.builder()
                .fullName("Markus Bat")
                .abilities("writes only clean code")
                .graduation("Software engineer")
                .workExperience("2 years")
                .build();

        entityManager.getTransaction().begin();

        entityManager.persist(profile);

        entityManager.getTransaction().commit();

        profiles.add(profile);
    }

    @After
    public void clean() {
        entityManager.getTransaction().begin();

        entityManager.createQuery("delete from Profile p").executeUpdate();

        entityManager.getTransaction().commit();
    }

    @Test
    public void testFindAll() {
        List<Profile> allProfiles = profileRepository.findAll();

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
        Assert.assertEquals(profile, profiles.get(chosenIndex));
    }

    @Test
    public void testFindByFullName() {
        final int chosenIndex = 0;
        final String testFullName = profiles.get(chosenIndex).getFullName();
        List<Profile> profileList = profileRepository.findByFullName(testFullName);
        profileList.forEach(System.out::println);

        Assert.assertNotNull(profileList);
        Assert.assertFalse(profileList.isEmpty());
        Assert.assertTrue(profileList.contains(profiles.get(chosenIndex)));
    }

    @Test
    public void testDeleteProfile() {
        final int chosenIndex = 0;
        final Long testId = profiles.get(chosenIndex).getId();
        profileRepository.deleteProfile(testId);

        BigInteger profileSingleResult = (BigInteger) entityManager
                .createNativeQuery("SELECT count(1) FROM PROFILES WHERE USER_ID=:id")
                .setParameter("id", testId)
                .getSingleResult();

        Assert.assertEquals(chosenIndex, profileSingleResult.intValue());
    }
}
