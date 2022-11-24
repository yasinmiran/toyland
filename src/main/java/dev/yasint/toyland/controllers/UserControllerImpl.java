package dev.yasint.toyland.controllers;

import dev.yasint.toyland.dtos.request.ContactUpdateDTO;
import dev.yasint.toyland.dtos.request.PaymentUpdateDTO;
import dev.yasint.toyland.dtos.response.user.UserDetailDTO;
import dev.yasint.toyland.dtos.response.user.UserDetailDTOFactory;
import dev.yasint.toyland.services.ContactService;
import dev.yasint.toyland.services.PaymentService;
import dev.yasint.toyland.services.UserDetailsImpl;
import dev.yasint.toyland.utils.Common;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserControllerImpl implements UserController {

    @Autowired
    private final UserDetailDTOFactory userDetailDTOFactory;
    @Autowired
    private final ContactService contactService;
    @Autowired
    private final PaymentService paymentService;

    @Override
    @GetMapping("/me")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MERCHANT', 'CUSTOMER', 'DRIVER')")
    public ResponseEntity<?> me() {
        UserDetailsImpl details = Common.getUserDetailsFromContext();
        UserDetailDTO<?> detailsObject = userDetailDTOFactory
                .createDetailsObject(details.getUser());
        return ResponseEntity.ok(detailsObject.getDetails());
    }

    @Override
    @PutMapping("/contact")
    @PreAuthorize("hasAnyAuthority('MERCHANT', 'CUSTOMER', 'DRIVER')")
    public ResponseEntity<?> updateContact(@RequestBody @Valid ContactUpdateDTO body) {
        UserDetailsImpl details = Common.getUserDetailsFromContext();
        return ResponseEntity.ok(
                contactService.updateContact(
                        details.getUser(),
                        body.transform()
                )
        );
    }

    @Override
    @PutMapping("/payment")
    @PreAuthorize("hasAnyAuthority('MERCHANT', 'CUSTOMER', 'DRIVER')")
    public ResponseEntity<?> updatePayment(@RequestBody @Valid PaymentUpdateDTO body) {
        UserDetailsImpl details = Common.getUserDetailsFromContext();
        return ResponseEntity.ok(
                paymentService.updatePayment(
                        details.getUser(),
                        body.transform()
                )
        );
    }

}

