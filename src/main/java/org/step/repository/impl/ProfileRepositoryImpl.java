package org.step.repository.impl;

import org.hibernate.annotations.QueryHints;
import org.step.entity.Profile;
import org.step.repository.ProfileRepository;
import org.step.repository.SessionFactoryCreator;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import java.util.List;

public class ProfileRepositoryImpl implements ProfileRepository {
    private static final EntityManager ENTITY_MANAGER = SessionFactoryCreator.getEntityManager();

    @Override
    public List<Profile> findAll() {
        ENTITY_MANAGER.getTransaction().begin();

        EntityGraph<?> entityGraph = ENTITY_MANAGER.getEntityGraph(Profile.Profile_View_Graph);

        List<Profile> profiles = ENTITY_MANAGER
                .createQuery("select p from Profile p", Profile.class)
                .setHint(QueryHints.READ_ONLY, true)
                // LoadGraph - загружает не только то, что мы прописали в графе, но так же все EAGER связи
                // FetchGraph - загружает только то, что мы прописали в графе
                .setHint("javax.persistence.fetchgraph", entityGraph)
                .getResultList();

        ENTITY_MANAGER.getTransaction().commit();

        return profiles;
    }

    @Override
    public Profile findById(Long id) {
        ENTITY_MANAGER.getTransaction().begin();

        EntityGraph<?> entityGraph = ENTITY_MANAGER.getEntityGraph(Profile.Profile_View_Graph);

        Profile profile = ENTITY_MANAGER
                .createQuery("select p from Profile p where p.id=:id", Profile.class)
                .setHint(QueryHints.READ_ONLY, true)
                .setParameter("id", id)
                .setHint("javax.persistence.loadgraph", entityGraph)
                .getSingleResult();

        ENTITY_MANAGER.getTransaction().commit();

        return profile;
    }

    @Override
    public List<Profile> findByFullName(String fullName) {
        ENTITY_MANAGER.getTransaction().begin();

        EntityGraph<?> entityGraph = ENTITY_MANAGER.getEntityGraph(Profile.Profile_View_Graph);

        List<Profile> profiles = ENTITY_MANAGER
                .createQuery("select p from Profile p where p.fullName=:fullName", Profile.class)
                .setHint(QueryHints.READ_ONLY, true)
                .setParameter("fullName", fullName)
                .setHint("javax.persistence.loadgraph", entityGraph)
                .getResultList();

        ENTITY_MANAGER.getTransaction().commit();

        return profiles;
    }

    @Override
    public void delete(Long id) {
        ENTITY_MANAGER.getTransaction().begin();

        ENTITY_MANAGER.createQuery("delete from View v where v.profile.id=:id")
                .setParameter("id", id)
                .executeUpdate();

        ENTITY_MANAGER.createQuery("delete from Profile p where p.id=:id")
                .setParameter("id", id)
                .executeUpdate();

        System.out.printf("Profile with id %d has been deleted\n", id);

        ENTITY_MANAGER.getTransaction().commit();
    }

    @Override
    public Profile save(Profile profile) {
        ENTITY_MANAGER.getTransaction().begin();

        ENTITY_MANAGER.persist(profile);

        ENTITY_MANAGER.getTransaction().commit();

        return profile;
    }
}