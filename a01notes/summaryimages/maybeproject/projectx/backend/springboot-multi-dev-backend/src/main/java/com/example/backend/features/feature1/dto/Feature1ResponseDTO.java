package com.example.backend.features.feature1.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Feature 1 Response DTO (Developer 1).
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Feature1ResponseDTO {
    private Long id;
    private String username;
    private String email;
    private String status;
    private String formattedCreatedAt;
}
