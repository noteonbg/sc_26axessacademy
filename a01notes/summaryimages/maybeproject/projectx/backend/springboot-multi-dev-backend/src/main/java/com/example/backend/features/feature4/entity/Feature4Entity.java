package com.example.backend.features.feature4.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Feature 4 Entity (Assigned to Developer 4).
 * Domain: Payment Gateway Integration
 */
@Entity
@Table(name = "feature4_payments")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Feature4Entity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long orderId;

    @Column(nullable = false)
    private BigDecimal amount;

    private String paymentMethod;
    private String transactionRef;
    private String status;
    private LocalDateTime processedAt;
}
