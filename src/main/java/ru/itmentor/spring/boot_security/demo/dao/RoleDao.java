package ru.itmentor.spring.boot_security.demo.dao;

import ru.itmentor.spring.boot_security.demo.model.Role;

public interface RoleDao {
    Role getByName(String name);
    void save(Role role);
}
