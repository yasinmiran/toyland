package dev.yasint.toyland.repositories;

import dev.yasint.toyland.models.Merchant;
import dev.yasint.toyland.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MerchantRepository extends JpaRepository<Merchant, Long> {

    Merchant findMerchantByUser(User user);

}
