package com.example.backend.features.feature1.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Feature 1 Entity (Assigned to Developer 1).
 * Domain: User Authentication & Profile
 */
@Entity
@Table(name = "feature1_users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Feature1Entity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String status;

    private LocalDateTime createdAt;
}
