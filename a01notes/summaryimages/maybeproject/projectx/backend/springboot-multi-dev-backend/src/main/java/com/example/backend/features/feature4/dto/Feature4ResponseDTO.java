package com.example.backend.features.feature4.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Feature 4 Response DTO (Developer 4).
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Feature4ResponseDTO {
    private Long id;
    private Long orderId;
    private BigDecimal amount;
    private String paymentMethod;
    private String transactionRef;
    private String status;
    private String formattedProcessedAt;
}
