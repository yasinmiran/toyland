package dev.yasint.toyland.controllers;

import dev.yasint.toyland.dtos.MerchantVerificationDTO;
import org.springframework.http.ResponseEntity;

public interface MerchantController {

    ResponseEntity<?> verify(MerchantVerificationDTO details);

}
