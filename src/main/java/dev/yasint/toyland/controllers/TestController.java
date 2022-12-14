package dev.yasint.toyland.controllers;

import dev.yasint.toyland.utils.Common;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class TestController {

    @GetMapping("/public-content")
    public String publicContent() {
        return "Public Content. Updated.";
    }

    @GetMapping("/all-authenticated-user-content")
    @PreAuthorize("hasAnyAuthority('CUSTOMER', 'DRIVER', 'ADMIN', 'MERCHANT')")
    public String allAuthenticatedUserContent() {
        return "All Authenticated User Content.";
    }

    @GetMapping("/admin-content")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String adminContent() {
        return "Admin Content.";
    }

    @GetMapping("/admin-and-merchant-content")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MERCHANT')")
    public String adminAndMerchantContent() {
        return "Admin and Merchant Content.";
    }

    @GetMapping("/authenticated-user-details")
    @PreAuthorize("hasAnyAuthority('CUSTOMER', 'DRIVER', 'ADMIN', 'MERCHANT')")
    public String authenticatedUserDetails() {
        UserDetails userDetails = Common.getUserDetailsFromContext();
        return userDetails.getUsername();
    }

}
