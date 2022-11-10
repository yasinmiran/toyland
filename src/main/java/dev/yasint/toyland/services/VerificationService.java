package dev.yasint.toyland.services;

import dev.yasint.toyland.exceptions.ResourceNotFoundException;
import dev.yasint.toyland.models.Merchant;
import dev.yasint.toyland.models.User;
import dev.yasint.toyland.models.verification.Verification;
import dev.yasint.toyland.models.enumerations.EVerificationStatus;

public interface VerificationService {

    Verification createVerificationRequest(Merchant merchant);

    Verification updateStatus(
            Long verificationId,
            EVerificationStatus status,
            User authority
    ) throws ResourceNotFoundException;

    boolean canRequestVerification(Merchant merchant);

}
