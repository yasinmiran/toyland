package dev.yasint.toyland.controllers;

import dev.yasint.toyland.dtos.request.MerchantVerificationDTO;
import dev.yasint.toyland.dtos.request.VerificationUpdateDTO;
import org.springframework.http.ResponseEntity;

public interface MerchantController {

    ResponseEntity<?> verify(MerchantVerificationDTO details);

    ResponseEntity<?> updateVerificationStatus(VerificationUpdateDTO body);

}
