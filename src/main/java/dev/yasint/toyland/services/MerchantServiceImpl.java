package dev.yasint.toyland.services;

import dev.yasint.toyland.exceptions.ResourceNotFoundException;
import dev.yasint.toyland.exceptions.UnableToSatisfyException;
import dev.yasint.toyland.models.Merchant;
import dev.yasint.toyland.models.Role;
import dev.yasint.toyland.models.User;
import dev.yasint.toyland.models.enumerations.ERole;
import dev.yasint.toyland.models.enumerations.EVerificationStatus;
import dev.yasint.toyland.models.verification.Verification;
import dev.yasint.toyland.repositories.MerchantRepository;
import dev.yasint.toyland.repositories.VerificationRepository;
import dev.yasint.toyland.utils.Common;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class MerchantServiceImpl implements MerchantService {

    private final MerchantRepository merchantRepository;
    private final VerificationService verificationService;
    private final VerificationRepository verificationRepository;

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

    @Override
    public Verification updateMerchantVerificationStatus(
            Long merchantId, EVerificationStatus status)
            throws ResourceNotFoundException, UnableToSatisfyException {

        User user = Common.getUserDetailsFromContext().getUser();

        if (user.getRoles().stream().map(Role::getName)
                .collect(Collectors.toSet()).contains(ERole.ADMIN)) {
            Merchant merchant = merchantRepository
                    .findById(merchantId)
                    .orElseThrow(RuntimeException::new);

            Verification verification = verificationRepository.findVerificationByMerchant(merchant);
            Verification updated = verificationService.updateStatus(verification.getId(), status, user);

            if (updated.getStatus() == status) {
                log.info("Successfully updated the verification status.");
            } else {
                log.info("Unable to update the verification status");
            }
            return updated;
        }

        throw new UnableToSatisfyException();

    }


}
