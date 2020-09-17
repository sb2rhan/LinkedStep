package org.step.repository;

import org.step.entity.Profile;

import java.util.List;

public interface ProfileRepository {

    List<Profile> findAll();

    Profile findById(Long id);

    List<Profile> findByFullName(String fullName);

    void deleteProfile(Long id);
}
