package com.example.backend.features.feature4.repository;

import com.example.backend.features.feature4.entity.Feature4Entity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Feature 4 JPA Repository (Developer 4).
 */
@Repository
public interface Feature4Repository extends JpaRepository<Feature4Entity, Long> {
    Optional<Feature4Entity> findByOrderId(Long orderId);
}
