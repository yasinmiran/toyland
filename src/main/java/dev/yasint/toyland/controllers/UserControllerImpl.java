package dev.yasint.toyland.controllers;

import dev.yasint.toyland.dtos.Transformable;
import dev.yasint.toyland.models.User;
import dev.yasint.toyland.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserControllerImpl implements UserController {

    private final UserService userService;

    @Override
    @PostMapping("/")
    public ResponseEntity<User> register(@RequestBody Transformable<User> body) {
        User u = userService.register(body.transform());

        return ResponseEntity.ok(u);
    }

    @Override
    @GetMapping("/")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        log.info("fetched {} users", users.size());
        return ResponseEntity.ok(users);
    }

    @Override
    @GetMapping("/login")
    public ResponseEntity<Object> login() {
        Map<String, String> res = new HashMap<>();
        res.put("message", "hiiiii yasinnnn");
        return ResponseEntity.ok(res);
    }

    @Override
    @PostMapping("/logout")
    public ResponseEntity<User> logout() {
        return null;
    }

}
