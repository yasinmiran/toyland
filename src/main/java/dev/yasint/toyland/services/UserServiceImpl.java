package dev.yasint.toyland.services;

import dev.yasint.toyland.exceptions.UnableToSatisfyException;
import dev.yasint.toyland.exceptions.UserExistsException;
import dev.yasint.toyland.models.Role;
import dev.yasint.toyland.models.enumerations.ERole;
import dev.yasint.toyland.models.user.Customer;
import dev.yasint.toyland.models.user.Driver;
import dev.yasint.toyland.models.user.Merchant;
import dev.yasint.toyland.models.user.User;
import dev.yasint.toyland.repositories.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final MerchantRepository merchantRepository;
    private final CustomerRepository customerRepository;

    private final DriverRepository driverRepository;

    @Override
    public void saveAllRoles(final List<Role> roles) {
        Set<Role> filteredRoles = new HashSet<>();
        for (Role role : roles) {
            if (!roleRepository.existsRoleByName(role.getName())) {
                filteredRoles.add(role);
            }
        }
        log.info("duplicated roles ignored | affected {} rows", filteredRoles.size());
        if (filteredRoles.size() > 0) {
            List<Role> saved = roleRepository.saveAll(filteredRoles);
            log.info("saved {} roles", saved.size());
        }
    }

    @Override
    public void checkUserExistence(String username) throws UserExistsException {
        if (userRepository.existsByUsername(username)) {
            throw new UserExistsException(username);
        }
    }

    @Override
    public void createAndSaveUser(User user, ERole role) throws UnableToSatisfyException {

        Role userRole = roleRepository
                .findByName(role)
                .orElseThrow(() -> new RuntimeException(role + " not found!"));

        if (userRole == null) {
            throw new UnableToSatisfyException();
        }

        // Set the desired roles for the user.
        user.setRoles(new HashSet<>(List.of(userRole)));
        // First save the basic user to the repository;
        userRepository.save(user);

        if (userRole.getName() == ERole.MERCHANT) {
            Merchant merchant = new Merchant();
            merchant.setUser(user);
            merchantRepository.save(merchant);
        } else if (userRole.getName() == ERole.CUSTOMER) {
            Customer customer = new Customer();
            customer.setUser(user);
            customerRepository.save(customer);
        } else {
            Driver driver = new Driver();
            driver.setUser(user);
            driverRepository.save(driver);
        }

    }

    @Override
    public User getUserReferenceById(Long id) {
        return userRepository.getReferenceById(id);
    }

}
