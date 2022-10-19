package dev.yasint.toyland.controllers;

import dev.yasint.toyland.dtos.Transformable;
import dev.yasint.toyland.models.User;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * Describes the User related controller class
 * interface methods. It should provide all the basic
 * requirements for functioning the
 */
public interface UserController {

    ResponseEntity<Object> login();

    ResponseEntity<User> logout();

    ResponseEntity<User> register(Transformable<User> userDTO);

    ResponseEntity<List<User>> getAllUsers();

}
