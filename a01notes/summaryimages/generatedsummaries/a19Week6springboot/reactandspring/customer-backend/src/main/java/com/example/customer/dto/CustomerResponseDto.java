package com.example.customer.dto; // Declares data transfer object package

/**
 * Data Transfer Object (DTO) for sending customer response data to client.
 * Decouples public JSON response structure from internal database entity.
 */
public class CustomerResponseDto {

    private Long customerId; // Customer ID sent to frontend
    private String name; // Customer name sent to frontend
    private String email; // Customer email sent to frontend
    private String location; // Customer location sent to frontend

    /**
     * Default constructor for Jackson JSON deserialization.
     */
    public CustomerResponseDto() {
    }

    /**
     * Parameterized constructor to initialize response DTO.
     */
    public CustomerResponseDto(Long customerId, String name, String email, String location) {
        this.customerId = customerId; // Set customer ID
        this.name = name; // Set customer name
        this.email = email; // Set customer email
        this.location = location; // Set customer location
    }

    public Long getCustomerId() {
        return customerId; // Return customer ID
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId; // Set customer ID
    }

    public String getName() {
        return name; // Return name
    }

    public void setName(String name) {
        this.name = name; // Set name
    }

    public String getEmail() {
        return email; // Return email
    }

    public void setEmail(String email) {
        this.email = email; // Set email
    }

    public String getLocation() {
        return location; // Return location
    }

    public void setLocation(String location) {
        this.location = location; // Set location
    }
}
