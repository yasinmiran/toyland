package dev.yasint.toyland.services;

import dev.yasint.toyland.models.Role;
import dev.yasint.toyland.repositories.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final RoleRepository roleRepository;

    @Override
    public List<Role> saveAllRoles(List<Role> roles) {
        if (roles.size() > 0) {
            List<Role> saved = roleRepository.saveAll(roles);
            log.info("saved {} roles", saved.size());
            return saved;
        }
        return new ArrayList<>();
    }

}
