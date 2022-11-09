package dev.yasint.toyland.exceptions;

public class UnableToSatisfyException extends Exception {

    public UnableToSatisfyException() {
        super("Unable to satisfy your request at this time.");
    }

}
