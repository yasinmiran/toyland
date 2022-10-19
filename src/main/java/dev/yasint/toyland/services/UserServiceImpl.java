package dev.yasint.toyland.services;

import dev.yasint.toyland.models.Role;
import dev.yasint.toyland.models.User;
import dev.yasint.toyland.repositories.RoleRepository;
import dev.yasint.toyland.repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    /**
     * Loads a user by its username. In this case, username
     * maps to user's email address as it's our primary reference
     * for user entity.
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        userRepository.findByEmail(username);

        return null;
    }

    @Override
    public void saveAllRoles(List<Role> roles) {
        if (roles.size() > 0) {
            List<Role> saved = roleRepository.saveAll(roles);
            log.info("saved {} roles", saved.size());
        }
    }

    @Override
    public User register(User user) {
        log.info("trying to save user {}", user.getEmail());
        return userRepository.save(user);
    }

    @Override
    public User login() {
        //
        return null;
    }

    @Override
    public void logout() {

    }

    @Override
    public User bindRole(User user, String roleName) {
        if (roleName != null) {
            Role role = roleRepository.findByName(roleName);
            log.info("bindRole: found role with name {} - identifier {}", roleName, role.getId());
            user.mergeRole(role);
        }
        return user;
    }

    @Override
    public User getUser(String email) {
        if (email != null) {
            return userRepository.findByEmail(email);
        }
        return null;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

}
