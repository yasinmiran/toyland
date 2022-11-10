package dev.yasint.toyland.repositories;

import dev.yasint.toyland.models.Merchant;
import dev.yasint.toyland.models.Verification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationRepository extends JpaRepository<Verification, Long> {

    Verification findVerificationByMerchant(Merchant merchant);

}
