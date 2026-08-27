package com.example.backend.features.feature3.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Feature 3 Entity (Assigned to Developer 3).
 * Domain: Cart & Order Management
 */
@Entity
@Table(name = "feature3_orders")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Feature3Entity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private Long catalogItemId;
    private Integer quantity;
    private BigDecimal totalPrice;
    private String status;
    private LocalDateTime createdAt;
}
