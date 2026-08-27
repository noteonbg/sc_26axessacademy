package com.example.backend.features.feature5.service.impl;

import com.example.backend.common.exception.ApiException;
import com.example.backend.common.util.DateTimeUtils;
import com.example.backend.features.feature5.dto.Feature5RequestDTO;
import com.example.backend.features.feature5.dto.Feature5ResponseDTO;
import com.example.backend.features.feature5.entity.Feature5Entity;
import com.example.backend.features.feature5.repository.Feature5Repository;
import com.example.backend.features.feature5.service.Feature5Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Feature 5 Implementation (Developer 5).
 */
@Service
@RequiredArgsConstructor
public class Feature5ServiceImpl implements Feature5Service {

    private final Feature5Repository repository;

    @Override
    @Transactional
    public Feature5ResponseDTO recordMetric(Feature5RequestDTO request) {
        Feature5Entity entity = Feature5Entity.builder()
                .metricName(request.getMetricName())
                .metricCategory(request.getMetricCategory())
                .metricValue(request.getMetricValue())
                .recordedAt(LocalDateTime.now())
                .build();

        Feature5Entity saved = repository.save(entity);
        return mapToDTO(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public Feature5ResponseDTO getMetricById(Long id) {
        Feature5Entity entity = repository.findById(id)
                .orElseThrow(() -> new ApiException("Feature5 metric not found with id: " + id, HttpStatus.NOT_FOUND));
        return mapToDTO(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Feature5ResponseDTO> getMetricsByCategory(String category) {
        return repository.findByMetricCategory(category).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Feature5ResponseDTO> getAllMetrics() {
        return repository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private Feature5ResponseDTO mapToDTO(Feature5Entity entity) {
        return Feature5ResponseDTO.builder()
                .id(entity.getId())
                .metricName(entity.getMetricName())
                .metricCategory(entity.getMetricCategory())
                .metricValue(entity.getMetricValue())
                .formattedRecordedAt(DateTimeUtils.formatLocalDateTime(entity.getRecordedAt()))
                .build();
    }
}
