package ru.itmentor.spring.boot_security.demo.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.itmentor.spring.boot_security.demo.model.User;
import ru.itmentor.spring.boot_security.demo.service.UserService;

@Controller
@RequestMapping
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    // --- Доступно только администратору (ROLE_ADMIN) ---
    @GetMapping("/admin/users")
    public String listUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "users"; // Представление со списком пользователей
    }

    @GetMapping("/admin/users/new")
    public String newUserForm(Model model) {
        model.addAttribute("user", new User());
        return "add-user"; // Форма создания нового пользователя
    }

    @PostMapping("/admin/users/add")
    public String addUser(@ModelAttribute User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword())); // зашифровать пароль
        userService.saveUser(user);
        return "redirect:/admin/users";
    }

    @GetMapping("/admin/users/{id}/edit")
    public String editUserForm(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        return "edit-user"; // Форма редактирования
    }

    @PostMapping("/admin/users/edit/{id}")
    public String editUser(@PathVariable("id") Long id, @ModelAttribute User updatedUser) {
        // Получаем пользователя из базы, чтобы сохранить старый пароль и роли
        User existingUser = userService.getUserById(id);

        // Сохраняем старый пароль, если поле пароля пустое
        if (updatedUser.getPassword() == null || updatedUser.getPassword().isEmpty()) {
            updatedUser.setPassword(existingUser.getPassword());
        }

        // Сохраняем роли пользователя, если они отсутствуют в обновлённой сущности
        if (updatedUser.getRoles() == null || updatedUser.getRoles().isEmpty()) {
            updatedUser.setRoles(existingUser.getRoles());
        }

        // Обновляем пользователя
        userService.updateUser(updatedUser);
        return "redirect:/admin/users";
    }

    @PostMapping("/admin/users/{id}/delete")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin/users";
    }

    // --- Доступ только пользователю (ROLE_USER) и администратору ---
    @GetMapping("/user")
    public String getUserProfile(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.getUserByUsername(userDetails.getUsername());

        if (user == null) {
            throw new RuntimeException("User not found: " + userDetails.getUsername());
        }

        model.addAttribute("user", user);
        return "user-profile";
    }

    @PostMapping("/user/edit")
    public String editUserProfile(@AuthenticationPrincipal UserDetails userDetails, @ModelAttribute User updatedUser) {
        User user = userService.getUserByUsername(userDetails.getUsername());
        updatedUser.setId(user.getId()); // Принудительно фиксируем ID текущего пользователя
        userService.updateUser(updatedUser);
        return "redirect:/user"; // Возвращаемся на профиль
    }
}
