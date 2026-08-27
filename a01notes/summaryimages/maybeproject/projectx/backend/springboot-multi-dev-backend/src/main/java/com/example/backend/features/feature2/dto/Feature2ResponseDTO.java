package com.example.backend.features.feature2.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Feature 2 Response DTO (Developer 2).
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Feature2ResponseDTO {
    private Long id;
    private String name;
    private BigDecimal price;
    private Integer stockQuantity;
    private String formattedCreatedAt;
}
