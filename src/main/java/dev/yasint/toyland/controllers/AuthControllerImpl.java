package dev.yasint.toyland.controllers;

import dev.yasint.toyland.dtos.*;
import dev.yasint.toyland.models.BasicUser;
import dev.yasint.toyland.models.enumerations.ERole;
import dev.yasint.toyland.models.Role;
import dev.yasint.toyland.repositories.RoleRepository;
import dev.yasint.toyland.repositories.UserRepository;
import dev.yasint.toyland.services.UserDetailsImpl;
import dev.yasint.toyland.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthControllerImpl implements AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private JWTUtils jwtUtils;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequestDTO loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);
        List<String> roles = userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .body(
                        new LoginResponseDTO(
                                jwtCookie.getValue(),
                                new UserInfoResponseDTO(
                                        userDetails.getId(),
                                        userDetails.getEmail(),
                                        roles
                                )
                        )
                );

    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody SignupRequestDTO signUpRequest) {

        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(
                            new MessageResponseDTO("Error: Email is already in use!")
                    );
        }

        final BasicUser basicUser = new BasicUser(
                signUpRequest.getUsername(),
                encoder.encode(signUpRequest.getPassword())
        );

        Role userRole = roleRepository
                .findByName(ERole.CUSTOMER)
                .orElseThrow(() -> new RuntimeException(ERole.CUSTOMER + " not found!"));

        if (userRole == null) {
            return ResponseEntity
                    .internalServerError()
                    .body(new MessageResponseDTO("cannot register at this time"));
        }

        basicUser.setRoles(new HashSet<>(List.of(userRole)));
        userRepository.save(basicUser);
        return ResponseEntity.ok(new MessageResponseDTO("user registered successfully!"));

    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
        return ResponseEntity
                .ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(new MessageResponseDTO("You've been signed out!"));
    }

}
