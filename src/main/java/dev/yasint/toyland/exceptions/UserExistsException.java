package dev.yasint.toyland.exceptions;

public class UserExistsException extends Exception {

    public UserExistsException(String username) {
        super(String.format("User with %s already exists in the system.", username));
    }

}
