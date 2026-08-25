package com.example.customer.exception; // Declares exception package namespace

/**
 * Custom runtime exception thrown when a customer cannot be found by ID.
 * Extends RuntimeException to allow unchecked exception throwing.
 */
public class CustomerNotFoundException extends RuntimeException {

    /**
     * Constructor accepting custom message string.
     */
    public CustomerNotFoundException(String message) {
        super(message); // Pass custom message to RuntimeException superclass
    }

    /**
     * Helper constructor taking missing customer ID.
     */
    public CustomerNotFoundException(Long id) {
        super("Customer with ID " + id + " was not found"); // Format standard error message
    }
}
