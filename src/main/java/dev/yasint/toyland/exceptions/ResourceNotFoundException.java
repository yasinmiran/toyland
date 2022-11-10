package dev.yasint.toyland.exceptions;

public class ResourceNotFoundException extends Exception {

    public ResourceNotFoundException() {
        super("Target resource is not found.");
    }

}
