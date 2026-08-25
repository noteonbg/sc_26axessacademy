package com.example.rectangle.exception;

/**
 * Custom Exception thrown when both length and breadth of rectangle are zero.
 */
public class ZeroDimensionsException extends RuntimeException {

    public ZeroDimensionsException() {
        super("Length and Breadth cannot both be zero!");
    }

    public ZeroDimensionsException(String message) {
        super(message);
    }
}
