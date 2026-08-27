package com.example.backend.features.feature2.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Feature 2 Entity (Assigned to Developer 2).
 * Domain: Product Catalog
 */
@Entity
@Table(name = "feature2_catalog")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Feature2Entity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private BigDecimal price;

    private Integer stockQuantity;

    private LocalDateTime createdAt;
}
