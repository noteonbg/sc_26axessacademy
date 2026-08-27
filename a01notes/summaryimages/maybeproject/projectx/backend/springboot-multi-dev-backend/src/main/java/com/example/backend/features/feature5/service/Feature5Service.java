package com.example.backend.features.feature5.service;

import com.example.backend.features.feature5.dto.Feature5RequestDTO;
import com.example.backend.features.feature5.dto.Feature5ResponseDTO;

import java.util.List;

/**
 * Feature 5 Service Interface (Developer 5).
 */
public interface Feature5Service {
    Feature5ResponseDTO recordMetric(Feature5RequestDTO request);
    Feature5ResponseDTO getMetricById(Long id);
    List<Feature5ResponseDTO> getMetricsByCategory(String category);
    List<Feature5ResponseDTO> getAllMetrics();
}
