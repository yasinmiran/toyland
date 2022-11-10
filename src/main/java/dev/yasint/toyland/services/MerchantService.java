package dev.yasint.toyland.services;

import dev.yasint.toyland.exceptions.UnableToSatisfyException;
import dev.yasint.toyland.models.User;
import dev.yasint.toyland.models.Verification;

public interface MerchantService {

    Verification requestVerification(User user, String taxId) throws UnableToSatisfyException;

}
