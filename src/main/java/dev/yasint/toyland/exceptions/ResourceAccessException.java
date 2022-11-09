package dev.yasint.toyland.exceptions;

public class ResourceAccessException extends Exception {

    public ResourceAccessException() {
        super("Unable to access the requested resource.");
    }

}
