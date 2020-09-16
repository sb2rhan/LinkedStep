package org.step.repository.impl;

import org.step.entity.Profile;
import org.step.repository.ProfileRepository;
import org.step.repository.SessionFactoryCreator;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class ProfileRepositoryImpl implements ProfileRepository {

    private final EntityManager entityManager = SessionFactoryCreator.getEntityManager();

    @Override
    public List<Profile> findAll() {
        return entityManager.createQuery("from Profile p", Profile.class).getResultList();
    }

    @Override
    public Optional<Profile> findById(Long id) {
        return Optional.ofNullable(
                entityManager.find(Profile.class, id)
        );
    }
}
