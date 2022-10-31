package dev.yasint.toyland.configs;

import dev.yasint.toyland.models.ERole;
import dev.yasint.toyland.models.Role;
import dev.yasint.toyland.models.User;
import dev.yasint.toyland.repositories.RoleRepository;
import dev.yasint.toyland.repositories.UserRepository;
import dev.yasint.toyland.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Slf4j
@Configuration
public class Bootstrapper {

    @Bean("create_roles")
    CommandLineRunner createRoles(@Autowired UserService userService) {
        return (String... args) -> {
            List<Role> roles = Arrays.asList(
                    new Role(ERole.ADMIN),
                    new Role(ERole.CUSTOMER),
                    new Role(ERole.MERCHANT),
                    new Role(ERole.DRIVER)
            );
            userService.saveAllRoles(roles);
        };
    }

    @Bean("create_admins")
    @DependsOn("create_roles")
    CommandLineRunner createAdmins(
            @Autowired RoleRepository roleRepository,
            @Autowired UserRepository userRepository,
            @Autowired PasswordEncoder encoder
    ) {
        return (String... args) -> {
            if (!userRepository.existsByUsername("admin@toyland.com")) {
                User admin = new User(
                        "admin@toyland.com",
                        encoder.encode("admin123")
                );
                Role role = roleRepository
                        .findByName(ERole.ADMIN)
                        .orElseThrow(() -> new RuntimeException(ERole.ADMIN + " role not found!"));
                admin.setRoles(new HashSet<>(List.of(role)));
                userRepository.save(admin);
            }
        };
    }

}
