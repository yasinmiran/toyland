package dev.yasint.toyland.services;

import dev.yasint.toyland.exceptions.ResourceNotFoundException;
import dev.yasint.toyland.models.Merchant;
import dev.yasint.toyland.models.User;
import dev.yasint.toyland.models.Verification;
import dev.yasint.toyland.models.enumerations.EVerificationStatus;
import dev.yasint.toyland.repositories.VerificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class VerificationServiceImpl implements VerificationService {

    private final VerificationRepository verificationRepository;

    @Override
    public Verification createVerificationRequest(Merchant merchant) {
        Verification verification = new Verification();
        verification.setMerchant(merchant);
        verification.setCreatedAt(LocalDateTime.now());
        verification.setStatus(EVerificationStatus.CREATED);
        verificationRepository.save(verification);
        return verification;
    }

    @Override
    public Verification updateStatus(
            Long verificationId,
            EVerificationStatus status,
            User authority
    ) throws ResourceNotFoundException {
        Verification verification = verificationRepository
                .findById(verificationId)
                .orElseThrow(ResourceNotFoundException::new);
        verification.setModifiedAt(LocalDateTime.now());
        verification.setAuthority(authority);
        verification.setStatus(status);
        return verification;
    }

    @Override
    public boolean canRequestVerification(Merchant merchant) {
        Verification verification = verificationRepository
                .findVerificationByMerchant(merchant);
        return verification == null;
    }

}
