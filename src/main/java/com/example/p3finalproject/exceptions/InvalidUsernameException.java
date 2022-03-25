package com.example.p3finalproject.exceptions;

public class InvalidUsernameException extends RuntimeException {
    public InvalidUsernameException() {
        super("A username must contain at least 5 alphanumerical characters.");
    }
}
