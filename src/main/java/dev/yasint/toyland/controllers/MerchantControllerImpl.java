package dev.yasint.toyland.controllers;

import dev.yasint.toyland.dtos.request.MerchantVerificationDTO;
import dev.yasint.toyland.dtos.request.VerificationUpdateDTO;
import dev.yasint.toyland.dtos.response.MessageResDTO;
import dev.yasint.toyland.exceptions.ResourceNotFoundException;
import dev.yasint.toyland.exceptions.UnableToSatisfyException;
import dev.yasint.toyland.models.User;
import dev.yasint.toyland.models.verification.Verification;
import dev.yasint.toyland.services.MerchantService;
import dev.yasint.toyland.utils.Common;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
    public ResponseEntity<?> verify(@Valid @RequestBody MerchantVerificationDTO details) {
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

    @Override
    @PostMapping("/verification/request")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> updateVerificationStatus(
            @RequestBody VerificationUpdateDTO body
    ) {
        try {
            Verification verification = merchantService
                    .updateMerchantVerificationStatus(
                            body.getMerchantId(), body.getStatus());
            return ResponseEntity.ok().body(verification);
        } catch (UnableToSatisfyException e) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResDTO(
                            "You already have a verification request in place."
                    ));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
