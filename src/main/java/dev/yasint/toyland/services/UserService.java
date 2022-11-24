package dev.yasint.toyland.services;

import dev.yasint.toyland.exceptions.UnableToSatisfyException;
import dev.yasint.toyland.exceptions.UserExistsException;
import dev.yasint.toyland.models.Role;
import dev.yasint.toyland.models.enumerations.ERole;
import dev.yasint.toyland.models.user.User;

import java.util.List;

public interface UserService {

    void saveAllRoles(List<Role> roles);

    void checkUserExistence(String username) throws UserExistsException;

    void createAndSaveUser(User user, ERole role) throws UnableToSatisfyException;

}
