package com.example.backend.features.feature2.repository;

import com.example.backend.features.feature2.entity.Feature2Entity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Feature 2 JPA Repository (Developer 2).
 */
@Repository
public interface Feature2Repository extends JpaRepository<Feature2Entity, Long> {
    List<Feature2Entity> findByNameContainingIgnoreCase(String keyword);
}
