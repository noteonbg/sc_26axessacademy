package com.example.backend.features.feature3.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Feature 3 Request DTO (Developer 3).
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Feature3RequestDTO {

    @NotNull(message = "User ID is required")
    private Long userId;

    @NotNull(message = "Catalog item ID is required")
    private Long catalogItemId;

    @NotNull(message = "Quantity is required")
    @Min(value = 1, message = "Quantity must be at least 1")
    private Integer quantity;
}
