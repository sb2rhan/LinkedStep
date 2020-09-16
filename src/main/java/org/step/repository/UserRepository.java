package org.step.repository;

import org.step.entity.User;

import java.util.List;

public interface UserRepository {

    void updateUsername(String username, Long id);

    User saveUser(User user);

    List<User> findAll();

    void deleteUser(Long id);
}
