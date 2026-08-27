package com.example.backend.features.feature4.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Feature 4 Request DTO (Developer 4).
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Feature4RequestDTO {

    @NotNull(message = "Order ID is required")
    private Long orderId;

    @NotNull(message = "Amount is required")
    @Min(value = 1, message = "Payment amount must be greater than 0")
    private BigDecimal amount;

    @NotBlank(message = "Payment method cannot be blank")
    private String paymentMethod;
}
