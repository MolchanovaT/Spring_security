package ru.itmentor.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String homePage(Model model) {
        return "index"; // Убедитесь, что шаблон "index.html" создан
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login"; // Убедитесь, что шаблон "login.html" существует
    }
}