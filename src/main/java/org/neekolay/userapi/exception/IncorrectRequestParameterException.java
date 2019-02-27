package org.neekolay.userapi.exception;

/**
 * Created by user on 27/02/2019.
 */
public class IncorrectRequestParameterException extends RuntimeException {
    public IncorrectRequestParameterException(String message) {
        super("Incorrect request parameter: " + message);
    }
}
