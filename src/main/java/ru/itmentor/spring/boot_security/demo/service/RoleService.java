package ru.itmentor.spring.boot_security.demo.service;

import ru.itmentor.spring.boot_security.demo.model.Role;

public interface RoleService {
    Role getByName(String name);
    Role getById(int id);
    Role save(Role role);
}
