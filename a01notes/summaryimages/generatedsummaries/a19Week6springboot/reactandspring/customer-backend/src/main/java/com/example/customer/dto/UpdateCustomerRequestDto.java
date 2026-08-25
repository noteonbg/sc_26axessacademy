package com.example.customer.dto; // Declares data transfer object package

import jakarta.validation.constraints.Email; // Validation annotation ensuring valid email format
import jakarta.validation.constraints.NotBlank; // Validation annotation ensuring non-null, non-empty text

/**
 * DTO for customer update requests.
 * STRICT RULE ENFORCED: ONLY email and location fields exist in this DTO.
 * customerId and name are omitted so they can NEVER be passed or updated by client.
 */
public class UpdateCustomerRequestDto {

    @NotBlank(message = "Email is required") // Validation rule: email cannot be null or blank spaces
    @Email(message = "Please provide a valid email address") // Validation rule: email must follow valid format
    private String email; // Customer email field (EDITABLE)

    @NotBlank(message = "Location is required") // Validation rule: location cannot be null or blank spaces
    private String location; // Customer location field (EDITABLE)

    /**
     * Default constructor for JSON deserialization.
     */
    public UpdateCustomerRequestDto() {
    }

    /**
     * Constructor initializing editable email and location.
     */
    public UpdateCustomerRequestDto(String email, String location) {
        this.email = email; // Assign email
        this.location = location; // Assign location
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
