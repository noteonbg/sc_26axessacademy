package com.example.customer.dto; // Declares DTO package namespace

import java.time.LocalDateTime; // Import LocalDateTime for response timestamping

/**
 * Generic API response wrapper class.
 * Standardizes API responses across all controller endpoints.
 * @param <T> Payload data type (e.g. List<CustomerResponseDto> or CustomerResponseDto)
 */
public class ApiResponse<T> {

    private boolean success; // Indicates if request succeeded (true) or failed (false)
    private String message; // Human-readable response message
    private T data; // Generic payload object containing returned data
    private LocalDateTime timestamp; // ISO timestamp when response was generated

    /**
     * Default constructor automatically initializing timestamp to current time.
     */
    public ApiResponse() {
        this.timestamp = LocalDateTime.now(); // Set current system date & time
    }

    /**
     * Helper constructor creating successful/custom API response.
     */
    public ApiResponse(boolean success, String message, T data) {
        this.success = success; // Set success flag
        this.message = message; // Set message string
        this.data = data; // Set payload data
        this.timestamp = LocalDateTime.now(); // Set current system date & time
    }

    public boolean isSuccess() {
        return success; // Return success boolean
    }

    public void setSuccess(boolean success) {
        this.success = success; // Set success boolean
    }

    public String getMessage() {
        return message; // Return message string
    }

    public void setMessage(String message) {
        this.message = message; // Set message string
    }

    public T getData() {
        return data; // Return payload data object
    }

    public void setData(T data) {
        this.data = data; // Set payload data object
    }

    public LocalDateTime getTimestamp() {
        return timestamp; // Return timestamp
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp; // Set timestamp
    }
}
