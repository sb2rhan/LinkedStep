package org.step.repository;

import org.step.entity.Profile;

import java.util.List;
import java.util.Optional;

public interface ProfileRepository {

    List<Profile> findAll();

    Optional<Profile> findById(Long id);
}
