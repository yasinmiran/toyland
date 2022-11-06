package dev.yasint.toyland.services;

import dev.yasint.toyland.models.Role;

import java.util.List;

public interface UserService {

    List<Role> saveAllRoles(List<Role> roles);

}
