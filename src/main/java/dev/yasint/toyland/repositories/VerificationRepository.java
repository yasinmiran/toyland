package dev.yasint.toyland.repositories;

import dev.yasint.toyland.models.Merchant;
import dev.yasint.toyland.models.verification.Verification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VerificationRepository extends JpaRepository<Verification, Long> {

    Verification findVerificationByMerchant(Merchant merchant);

}
