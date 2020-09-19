package org.step.repository;

import java.util.List;

public interface CrudRepository<T> {

    List<T> findAll();

    void delete(Long id);

    T save(T t);
}
