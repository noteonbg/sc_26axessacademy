package com.example.backend.features.feature3.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Feature 3 Response DTO (Developer 3).
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Feature3ResponseDTO {
    private Long id;
    private Long userId;
    private Long catalogItemId;
    private Integer quantity;
    private BigDecimal totalPrice;
    private String status;
    private String formattedCreatedAt;
}
