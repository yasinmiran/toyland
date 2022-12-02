package dev.yasint.toyland.controllers;

import dev.yasint.toyland.dtos.request.UserSignupReqDTO;
import dev.yasint.toyland.dtos.request.LoginReqDTO;
import org.springframework.http.ResponseEntity;

public interface AuthController {

    ResponseEntity<?> login(LoginReqDTO body);

    ResponseEntity<?> register(UserSignupReqDTO body);

    ResponseEntity<?> logout();

}
