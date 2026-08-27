package com.example.backend.features.feature5.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Feature 5 Entity (Assigned to Developer 5).
 * Domain: Analytics & System Audit Metrics
 */
@Entity
@Table(name = "feature5_analytics")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Feature5Entity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String metricName;

    private String metricCategory;

    @Column(nullable = false)
    private Long metricValue;

    private LocalDateTime recordedAt;
}
