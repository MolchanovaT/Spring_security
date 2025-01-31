package ru.itmentor.spring.boot_security.demo.dto;

import java.util.HashSet;
import java.util.Set;

public class UserDTO {
    private String username;
    private String password;
    private String email;
    private Set<Long> roleIds;

    public UserDTO() {
    }

    public UserDTO(String username, String password, String email, Set<Long> roleIds) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.roleIds = roleIds;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Long> getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(Set<Long> roleIds) {
        this.roleIds = roleIds;
    }
}