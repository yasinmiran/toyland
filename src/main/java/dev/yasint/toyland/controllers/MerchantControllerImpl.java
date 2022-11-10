package dev.yasint.toyland.controllers;

import dev.yasint.toyland.dtos.MerchantVerificationDTO;
import dev.yasint.toyland.dtos.response.MessageResDTO;
import dev.yasint.toyland.exceptions.UnableToSatisfyException;
import dev.yasint.toyland.models.User;
import dev.yasint.toyland.models.Verification;
import dev.yasint.toyland.services.MerchantService;
import dev.yasint.toyland.utils.Common;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/api/merchant")
@RequiredArgsConstructor
public class MerchantControllerImpl implements MerchantController {

    private final MerchantService merchantService;

    @Override
    @PostMapping("/verify")
    @PreAuthorize("hasAuthority('MERCHANT')")
    public ResponseEntity<?> verify(MerchantVerificationDTO details) {
        User user = Common.getUserDetailsFromContext().getUser();
        try {
            Verification verification = merchantService
                    .requestVerification(user, details.getTaxId());
            return ResponseEntity.ok().body(verification);
        } catch (UnableToSatisfyException e) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResDTO(
                            "You already have a verification request in place."
                    ));
        }
    }

}
