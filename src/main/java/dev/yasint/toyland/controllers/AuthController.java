package dev.yasint.toyland.controllers;

import dev.yasint.toyland.dtos.LoginRequestDTO;
import dev.yasint.toyland.dtos.SignupRequestDTO;
import org.springframework.http.ResponseEntity;

public interface AuthController {

    ResponseEntity<?> login(LoginRequestDTO loginRequestDTO);

    ResponseEntity<?> register(SignupRequestDTO signupRequestDTO);

    ResponseEntity<?> logout();

}
