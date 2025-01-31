package ru.itmentor.spring.boot_security.demo.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import ru.itmentor.spring.boot_security.demo.model.Role;

@Repository
public class RoleDaoImpl implements RoleDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Role getByName(String name) {
        return entityManager.createQuery("SELECT r FROM Role r WHERE r.name = :name", Role.class)
                .setParameter("name", name)
                .getSingleResult();

    }

    @Override
    public Role getById(int id) {
        return entityManager.createQuery("SELECT r FROM Role r WHERE r.id = :id", Role.class)
                .setParameter("id", id)
                .getSingleResult();
    }

    @Override
    public void save(Role role) {
        entityManager.persist(role);
    }
}
