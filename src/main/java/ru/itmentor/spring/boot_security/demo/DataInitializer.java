//package ru.itmentor.spring.boot_security.demo;
//
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Component;
//import ru.itmentor.spring.boot_security.demo.model.Role;
//import ru.itmentor.spring.boot_security.demo.model.User;
//import ru.itmentor.spring.boot_security.demo.service.RoleService;
//import ru.itmentor.spring.boot_security.demo.service.UserService;
//
//import java.util.Optional;
//import java.util.Set;
//
//@Component
//public class DataInitializer implements CommandLineRunner {
//    private final UserService userService;
//    private final RoleService roleService;
//    private final PasswordEncoder passwordEncoder;
//
//    public DataInitializer(UserService userService, RoleService roleService, PasswordEncoder passwordEncoder) {
//        this.userService = userService;
//        this.roleService = roleService;
//        this.passwordEncoder = passwordEncoder;
//    }
//
//    @Override
//    public void run(String... args) {
//        Role adminRole = Optional.ofNullable(roleService.getByName("ROLE_ADMIN"))
//                .orElseGet(() -> roleService.save(new Role("ROLE_ADMIN")));
//
//        Role userRole = Optional.ofNullable(roleService.getByName("ROLE_USER"))
//                .orElseGet(() -> roleService.save(new Role("ROLE_USER")));
//
//
//        User admin = new User("admin", passwordEncoder.encode("admin"), "admin@example.com", Set.of(adminRole, userRole));
//        User user = new User("user", passwordEncoder.encode("user"), "user@example.com", Set.of(userRole));
////        admin.addRole(adminRole);
////        admin.addRole(userRole);
////        user.addRole(userRole);
//        userService.saveUser(admin);
//        userService.saveUser(user);
//    }
//}
