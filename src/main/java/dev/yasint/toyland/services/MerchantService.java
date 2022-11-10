package dev.yasint.toyland.services;

import dev.yasint.toyland.exceptions.ResourceNotFoundException;
import dev.yasint.toyland.exceptions.UnableToSatisfyException;
import dev.yasint.toyland.models.User;
import dev.yasint.toyland.models.enumerations.EVerificationStatus;
import dev.yasint.toyland.models.verification.Verification;

public interface MerchantService {

    Verification requestVerification(User user, String taxId) throws UnableToSatisfyException;

    Verification updateMerchantVerificationStatus(Long merchantId, EVerificationStatus status) throws ResourceNotFoundException, UnableToSatisfyException;

}
