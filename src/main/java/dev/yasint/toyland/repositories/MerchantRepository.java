package dev.yasint.toyland.repositories;

import dev.yasint.toyland.models.user.Merchant;
import dev.yasint.toyland.models.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MerchantRepository extends JpaRepository<Merchant, Long> {

    Merchant findMerchantByUser(User user);

}
