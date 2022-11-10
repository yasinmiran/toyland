package dev.yasint.toyland.configs;

import dev.yasint.toyland.models.*;
import dev.yasint.toyland.models.enumerations.ERole;
import dev.yasint.toyland.repositories.*;
import dev.yasint.toyland.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
    CommandLineRunner createAdmins(
            @Autowired RoleRepository roleRepository,
            @Autowired UserRepository userRepository,
            @Autowired CustomerRepository customerRepository,
            @Autowired MerchantRepository merchantRepository,
            @Autowired ContactRepository contactRepository,
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

            if (!userRepository.existsByUsername("merchant@toyland.com")) {
                User user = new User(
                        "merchant@toyland.com",
                        encoder.encode("merchant123")
                );
                Role role = roleRepository
                        .findByName(ERole.MERCHANT)
                        .orElseThrow(() -> new RuntimeException(ERole.MERCHANT + " role not found!"));
                user.setRoles(new HashSet<>(List.of(role)));
                Merchant merchant = new Merchant();
                Contact contact = Contact
                        .builder()
                        .addressLine1("42 Maine Street")
                        .county("Dublin")
                        .country("Ireland")
                        .postcode("VP54K0")
                        .mobileNo("+353894900445")
                        .build();
                merchant.setUser(user);
                merchant.setContact(contact);
                userRepository.save(user);
                contactRepository.save(contact);
                merchantRepository.save(merchant);
            }

            if (!userRepository.existsByUsername("customer@toyland.com")) {
                User merchant = new User(
                        "customer@toyland.com",
                        encoder.encode("customer123")
                );
                Role role = roleRepository
                        .findByName(ERole.CUSTOMER)
                        .orElseThrow(() -> new RuntimeException(ERole.CUSTOMER + " role not found!"));
                merchant.setRoles(new HashSet<>(List.of(role)));
                userRepository.save(merchant);
                Customer association = new Customer();
                association.setUser(merchant);
                customerRepository.save(association);
            }
        };
    }

}
