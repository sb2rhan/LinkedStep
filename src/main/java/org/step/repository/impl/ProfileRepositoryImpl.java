package org.step.repository.impl;

import org.step.entity.Profile;
import org.step.repository.ProfileRepository;

import java.util.List;

public class ProfileRepositoryImpl implements ProfileRepository {

    @Override
    public List<Profile> findAll() {
        return null;
    }

    @Override
    public Profile findById(Long id) {
        return null;
    }

    @Override
    public List<Profile> findByFullName(String fullName) {
        return null;
    }

    @Override
    public void deleteProfile(Long id) {

    }
}