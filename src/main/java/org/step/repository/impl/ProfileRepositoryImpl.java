package org.step.repository.impl;

import org.step.repository.ProfileRepository;
import org.step.repository.SessionFactoryCreator;

import javax.persistence.EntityManager;

public class ProfileRepositoryImpl implements ProfileRepository {

    private final EntityManager entityManager = SessionFactoryCreator.getEntityManager();
}
