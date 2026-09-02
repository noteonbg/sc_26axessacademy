package com.example.apitestingpoc.exception;

/**
 * Custom runtime exception thrown when a requested Account resource is not found.
 */
public class AccountNotFoundException extends RuntimeException {
    public AccountNotFoundException(String message) {
        super(message);
    }
}
