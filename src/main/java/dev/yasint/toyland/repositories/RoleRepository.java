package dev.yasint.toyland.repositories;

import dev.yasint.toyland.models.enumerations.ERole;
import dev.yasint.toyland.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(ERole name);

    boolean existsRoleByName(ERole name);

}
