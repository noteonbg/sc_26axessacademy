package com.example.backend.features.feature1.repository;

import com.example.backend.features.feature1.entity.Feature1Entity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Feature 1 JPA Repository (Developer 1).
 */
@Repository
public interface Feature1Repository extends JpaRepository<Feature1Entity, Long> {
    Optional<Feature1Entity> findByUsername(String username);
}
