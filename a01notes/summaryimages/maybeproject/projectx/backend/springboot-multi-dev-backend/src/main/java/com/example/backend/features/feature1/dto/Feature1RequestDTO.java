package com.example.backend.features.feature1.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Feature 1 Request DTO (Developer 1).
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Feature1RequestDTO {

    @NotBlank(message = "Username cannot be blank")
    private String username;

    @Email(message = "Email must be a valid email format")
    @NotBlank(message = "Email cannot be blank")
    private String email;
}
