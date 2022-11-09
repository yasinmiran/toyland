package dev.yasint.toyland.repositories;

import dev.yasint.toyland.models.Merchant;
import dev.yasint.toyland.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface MerchantRepository extends JpaRepository<Merchant, Long> {
    Optional<Merchant> findByUserId(Long id);

    Merchant findMerchantByUser(User user);

    Merchant findMerchantByUser(User user);

}
