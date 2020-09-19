package org.step.repository;

import org.step.entity.Profile;

import java.util.List;

public interface ProfileRepository extends CrudRepository<Profile> {

    Profile findById(Long id);

    List<Profile> findByFullName(String fullName);
}
