package com.example.p3finalproject.exceptions;

public class IncorrectUsernameException extends Exception {
    public IncorrectUsernameException() {
        super("Username does not exist");
    }
}
