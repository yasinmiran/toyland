package dev.yasint.toyland.services;

import dev.yasint.toyland.exceptions.ResourceNotFoundException;
import dev.yasint.toyland.models.user.Merchant;
import dev.yasint.toyland.models.user.User;
import dev.yasint.toyland.models.enumerations.EVerificationStatus;
import dev.yasint.toyland.models.verification.*;
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
        VerificationCommand command = new CreatedVerificationCommand(verification);
        command.execute();
        verificationRepository.save(verification);
        return verification;
    }

    // -----------------
    // STATUS        > APPROVE,
    //               > IN_PROGRESS
    // -----------------

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
        switch (status) {
            case CREATED -> new CreatedVerificationCommand(verification).execute();
            case IN_PROGRESS -> new InProgressVerificationCommand(verification).execute();
            case APPROVED -> new ApprovedVerificationCommand(verification).execute();
            case DENIED -> new DeniedVerificationCommand(verification).execute();
        }
        return verificationRepository.save(verification);
    }

    @Override
    public boolean canRequestVerification(Merchant merchant) {
        Verification verification = verificationRepository.findVerificationByMerchant(merchant);
        return verification == null;
    }

}
