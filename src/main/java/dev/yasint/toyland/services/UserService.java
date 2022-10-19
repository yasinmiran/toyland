package dev.yasint.toyland.services;

import dev.yasint.toyland.models.Role;
import dev.yasint.toyland.models.User;

import java.util.List;

public interface UserService {

    void saveAllRoles(List<Role> roles);

    User register(User user);

    User login();

    void logout();

    User bindRole(User user, String roleName);

    User getUser(String email);

    List<User> getAllUsers();

}
