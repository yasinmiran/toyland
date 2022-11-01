package dev.yasint.toyland.repositories;

import dev.yasint.toyland.models.BasicUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<BasicUser, Long> {

    Optional<BasicUser> findByUsername(String username);

    Boolean existsByUsername(String username);

}
