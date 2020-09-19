package org.step.repository;

import org.step.entity.User;

import java.util.List;

public interface UserRepository extends CrudRepository<User> {

    void updateUsername(String username, Long id);
}
