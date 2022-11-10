package dev.yasint.toyland.services;

import dev.yasint.toyland.exceptions.UnableToSatisfyException;
import dev.yasint.toyland.models.Merchant;
import dev.yasint.toyland.models.User;
import dev.yasint.toyland.models.verification.Verification;
import dev.yasint.toyland.repositories.MerchantRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MerchantServiceImpl implements MerchantService {

    private final MerchantRepository merchantRepository;
    private final VerificationService verificationService;

    @Override
    public Verification requestVerification(User user, String taxId) throws UnableToSatisfyException {
        Merchant merchant = merchantRepository.findMerchantByUser(user);
        if (!verificationService.canRequestVerification(merchant)) {
            throw new UnableToSatisfyException();
        }
        merchant.setTaxId(taxId);
        merchantRepository.save(merchant);
        return verificationService.createVerificationRequest(merchant);
    }


}
