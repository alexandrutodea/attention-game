package com.example.p3finalproject.exceptions;

public class EmptyFieldsException extends RuntimeException {
    public EmptyFieldsException() {
        super("No fields should be empty");
    }
}
