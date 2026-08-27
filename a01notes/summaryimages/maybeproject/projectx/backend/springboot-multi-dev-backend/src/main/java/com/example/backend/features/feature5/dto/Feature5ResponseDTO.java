package com.example.backend.features.feature5.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Feature 5 Response DTO (Developer 5).
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Feature5ResponseDTO {
    private Long id;
    private String metricName;
    private String metricCategory;
    private Long metricValue;
    private String formattedRecordedAt;
}
