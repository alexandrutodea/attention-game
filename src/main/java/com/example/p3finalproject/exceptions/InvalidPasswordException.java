package com.example.p3finalproject.exceptions;

public class InvalidPasswordException extends RuntimeException {
    public InvalidPasswordException() {
        super("A password must contain at least 5 alphanumerical characters");
    }
}
