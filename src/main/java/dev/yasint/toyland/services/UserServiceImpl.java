package dev.yasint.toyland.services;

import dev.yasint.toyland.models.Role;
import dev.yasint.toyland.repositories.RoleRepository;
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

}
