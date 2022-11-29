package dev.yasint.toyland.controllers;

import dev.yasint.toyland.dtos.request.LoginReqDTO;
import dev.yasint.toyland.dtos.request.UserSignupReqDTO;
import dev.yasint.toyland.dtos.response.LoginResDTO;
import dev.yasint.toyland.dtos.response.MessageResDTO;
import dev.yasint.toyland.dtos.response.UserInfoResDTO;
import dev.yasint.toyland.exceptions.UnableToSatisfyException;
import dev.yasint.toyland.exceptions.UserExistsException;
import dev.yasint.toyland.models.user.User;
import dev.yasint.toyland.models.enumerations.ERole;
import dev.yasint.toyland.services.UserDetailsImpl;
import dev.yasint.toyland.services.UserService;
import dev.yasint.toyland.utils.JWTUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthControllerImpl implements AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private JWTUtils jwtUtils;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginReqDTO body) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        body.getUsername(),
                        body.getPassword()
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
                        new LoginResDTO(
                                jwtCookie.getValue(),
                                new UserInfoResDTO(
                                        userDetails.getId(),
                                        userDetails.getEmail(),
                                        roles
                                )
                        )
                );

    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody UserSignupReqDTO body) {

        ERole eRole = body.isMerchant()
                ? ERole.MERCHANT
                : ERole.CUSTOMER;

        log.info("{} register request", eRole);

        try {

            userService.checkUserExistence(body.getUsername());

            final User user = new User(
                    body.getUsername(),
                    encoder.encode(body.getPassword())
            );

            userService.createAndSaveUser(user, eRole);

        } catch (UserExistsException e) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResDTO(e.getMessage()));
        } catch (UnableToSatisfyException e) {
            return ResponseEntity
                    .internalServerError()
                    .body(new MessageResDTO(e.getMessage()));
        }

        return ResponseEntity.ok(new MessageResDTO(eRole.name() + " user registered successfully!"));

    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
        return ResponseEntity
                .ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(new MessageResDTO("You've been signed out!"));
    }

    @Override
    @PostMapping("/register-driver")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> registerDriver(@Valid @RequestBody UserSignupReqDTO body) {

        ERole eRole = ERole.DRIVER;

        try {

            userService.checkUserExistence(body.getUsername());

            final User user = new User(
                    body.getUsername(),
                    encoder.encode(body.getPassword()),
                    body.getName()
            );

            userService.createAndSaveUser(user, eRole);

        } catch (UserExistsException e) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResDTO(e.getMessage()));
        } catch (UnableToSatisfyException e) {
            return ResponseEntity
                    .internalServerError()
                    .body(new MessageResDTO(e.getMessage()));
        }

        return ResponseEntity.ok(new MessageResDTO(eRole.name() + " user registered successfully!"));
    }

}
