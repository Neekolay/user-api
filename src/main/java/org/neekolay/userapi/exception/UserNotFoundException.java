package org.neekolay.userapi.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String email) {
        super("User with id " + email + " is not found");
    }
}
