package com.example.p3finalproject.exceptions;

public class EmptyDatabaseException extends Exception {
    public EmptyDatabaseException() {
        super("No users exist in the database");
    }
}
