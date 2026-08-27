package com.example.backend.features.feature5.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Feature 5 Request DTO (Developer 5).
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Feature5RequestDTO {

    @NotBlank(message = "Metric name cannot be blank")
    private String metricName;

    @NotBlank(message = "Metric category cannot be blank")
    private String metricCategory;

    @NotNull(message = "Metric value is required")
    @Min(value = 0, message = "Metric value must be non-negative")
    private Long metricValue;
}
