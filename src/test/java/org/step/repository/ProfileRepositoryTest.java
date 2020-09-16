package org.step.repository;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.step.entity.Profile;
import org.step.repository.impl.ProfileRepositoryImpl;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProfileRepositoryTest {

    private final ProfileRepository profileRepository = new ProfileRepositoryImpl();
    private final EntityManager entityManager = SessionFactoryCreator.getEntityManager();
    private final List<Profile> profiles = new ArrayList<>();

    @Before
    public void setup() {
        Profile profile = Profile.builder()
                .abilities("abilities")
                .graduation("graduation")
                .workExperience("experience")
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
    public void findAllTest() {
        List<Profile> profileList = profileRepository.findAll();

        Assert.assertNotNull(profileList);
        Assert.assertFalse(profileList.isEmpty());
    }

    @Test
    public void findByIdTest() {
        final Long id = profiles.get(0).getId();

        Optional<Profile> profile = profileRepository.findById(id);

        Assert.assertTrue(profile.isPresent());
        Assert.assertEquals(id, profile.get().getId());
    }
}
