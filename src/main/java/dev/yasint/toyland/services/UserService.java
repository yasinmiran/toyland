package dev.yasint.toyland.services;

import dev.yasint.toyland.exceptions.UnableToSatisfyException;
import dev.yasint.toyland.exceptions.UserExistsException;
import dev.yasint.toyland.models.Role;
import dev.yasint.toyland.models.User;
import dev.yasint.toyland.models.enumerations.ERole;

import java.util.List;

public interface UserService {

    List<Role> saveAllRoles(List<Role> roles);

    void checkUserExistence(String username) throws UserExistsException;

    User createAndSaveUser(User user, ERole role) throws UnableToSatisfyException;

    User getUserReferenceById(Long id);
}
