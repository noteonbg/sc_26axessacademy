package com.example.backend.features.feature5.repository;

import com.example.backend.features.feature5.entity.Feature5Entity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Feature 5 JPA Repository (Developer 5).
 */
@Repository
public interface Feature5Repository extends JpaRepository<Feature5Entity, Long> {
    List<Feature5Entity> findByMetricCategory(String metricCategory);
}
