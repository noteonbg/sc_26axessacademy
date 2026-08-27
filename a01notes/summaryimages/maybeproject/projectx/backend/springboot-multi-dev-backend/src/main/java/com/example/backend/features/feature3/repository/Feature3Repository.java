package com.example.backend.features.feature3.repository;

import com.example.backend.features.feature3.entity.Feature3Entity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Feature 3 JPA Repository (Developer 3).
 */
@Repository
public interface Feature3Repository extends JpaRepository<Feature3Entity, Long> {
    List<Feature3Entity> findByUserId(Long userId);
}
