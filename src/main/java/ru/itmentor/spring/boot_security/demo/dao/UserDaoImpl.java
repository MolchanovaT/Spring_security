package ru.itmentor.spring.boot_security.demo.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import ru.itmentor.spring.boot_security.demo.model.Role;
import ru.itmentor.spring.boot_security.demo.model.User;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<User> getAllUsers() {
        return entityManager.createQuery("FROM User", User.class).getResultList();
    }

    @Override
    public User getUserById(Long id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public User getUserByUsername(String username) {
        return entityManager.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class)
                .setParameter("username", username)
                .getSingleResult();
    }

    @Override
    public void saveUser(User user) {
        // Для каждой роли проверить, существует ли она в базе
        user.setRoles(user.getRoles().stream()
                .map(role -> entityManager.find(Role.class, role.getId()))
                .collect(Collectors.toSet()));

        // Сохраняем пользователя; если он ещё не существует, создаётся новый
        entityManager.merge(user);
    }

    @Override
    public void updateUser(User user) {
        // Для каждой роли проверить, существует ли она в базе данных
        user.setRoles(user.getRoles().stream()
                .map(role -> {
                    // Если роль не найдена в БД, вернуть её как есть
                    Role existingRole = entityManager.find(Role.class, role.getId());
                    return existingRole != null ? existingRole : role;
                })
                .collect(Collectors.toSet()));

        // Сохраняем изменения
        entityManager.merge(user);
    }

    @Override
    public void deleteUser(Long id) {
        User user = getUserById(id);
        if (user != null) {
            entityManager.remove(user);
        }
    }
}
