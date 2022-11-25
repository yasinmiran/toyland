package dev.yasint.toyland.services;

import dev.yasint.toyland.exceptions.UnableToSatisfyException;
import dev.yasint.toyland.exceptions.UserExistsException;
import dev.yasint.toyland.models.Customer;
import dev.yasint.toyland.models.Merchant;
import dev.yasint.toyland.models.Role;
import dev.yasint.toyland.models.User;
import dev.yasint.toyland.models.enumerations.ERole;
import dev.yasint.toyland.repositories.CustomerRepository;
import dev.yasint.toyland.repositories.MerchantRepository;
import dev.yasint.toyland.repositories.RoleRepository;
import dev.yasint.toyland.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    @Override
    public List<Role> saveAllRoles(final List<Role> roles) {
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
            return saved;
        }
        return new ArrayList<>();
    }

    @Override
    public void checkUserExistence(String username) throws UserExistsException {
        if (userRepository.existsByUsername(username)) {
            throw new UserExistsException(username);
        }
    }

    @Override
    public User createAndSaveUser(User user, ERole role) throws UnableToSatisfyException {

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
        } else {
            Customer customer = new Customer();
            customer.setUser(user);
            customerRepository.save(customer);
        }

        return user;

    }

    @Override
    public User getUserReferenceById(Long id) {
        return userRepository.getReferenceById(id);
    }

}
