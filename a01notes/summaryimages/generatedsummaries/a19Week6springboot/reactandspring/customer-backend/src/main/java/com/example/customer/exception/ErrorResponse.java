package com.example.customer.exception; // Declares exception package namespace

import java.time.LocalDateTime; // Imports LocalDateTime for error timestamping
import java.util.Map; // Imports Map interface for validation error key-value pairs

/**
 * Standardized Error Response object returned when an exception occurs.
 */
public class ErrorResponse {

    private LocalDateTime timestamp; // Timestamp when error occurred
    private int status; // HTTP status code (e.g. 400, 404, 500)
    private String error; // HTTP status reason phrase (e.g. "Not Found", "Bad Request")
    private String message; // Human-readable error description
    private Map<String, String> validationErrors; // Map of field-level validation errors (field -> message)

    /**
     * Default constructor setting current timestamp.
     */
    public ErrorResponse() {
        this.timestamp = LocalDateTime.now(); // Set current system date & time
    }

    /**
     * Constructor for simple error responses without field validation details.
     */
    public ErrorResponse(int status, String error, String message) {
        this.timestamp = LocalDateTime.now(); // Set current system date & time
        this.status = status; // Set HTTP status code number
        this.error = error; // Set HTTP error title phrase
        this.message = message; // Set detailed error message
    }

    /**
     * Constructor for validation errors including field error map.
     */
    public ErrorResponse(int status, String error, String message, Map<String, String> validationErrors) {
        this.timestamp = LocalDateTime.now(); // Set current system date & time
        this.status = status; // Set HTTP status code number
        this.error = error; // Set HTTP error title phrase
        this.message = message; // Set detailed error message
        this.validationErrors = validationErrors; // Set validation error map
    }

    public LocalDateTime getTimestamp() {
        return timestamp; // Return timestamp
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp; // Set timestamp
    }

    public int getStatus() {
        return status; // Return status code
    }

    public void setStatus(int status) {
        this.status = status; // Set status code
    }

    public String getError() {
        return error; // Return error string
    }

    public void setError(String error) {
        this.error = error; // Set error string
    }

    public String getMessage() {
        return message; // Return message string
    }

    public void setMessage(String message) {
        this.message = message; // Set message string
    }

    public Map<String, String> getValidationErrors() {
        return validationErrors; // Return validation error map
    }

    public void setValidationErrors(Map<String, String> validationErrors) {
        this.validationErrors = validationErrors; // Set validation error map
    }
}
