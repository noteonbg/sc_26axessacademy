package com.example.backend.features.feature1.service.impl;

import com.example.backend.common.exception.ApiException;
import com.example.backend.common.util.DateTimeUtils;
import com.example.backend.features.feature1.dto.Feature1RequestDTO;
import com.example.backend.features.feature1.dto.Feature1ResponseDTO;
import com.example.backend.features.feature1.entity.Feature1Entity;
import com.example.backend.features.feature1.repository.Feature1Repository;
import com.example.backend.features.feature1.service.Feature1Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Feature 1 Business Logic Implementation (Developer 1).
 */
@Service
@RequiredArgsConstructor
public class Feature1ServiceImpl implements Feature1Service {

    private final Feature1Repository repository;

    @Override
    @Transactional
    public Feature1ResponseDTO createFeature1(Feature1RequestDTO request) {
        if (repository.findByUsername(request.getUsername()).isPresent()) {
            throw new ApiException("Username already exists: " + request.getUsername(), HttpStatus.CONFLICT);
        }

        Feature1Entity entity = Feature1Entity.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .status("ACTIVE")
                .createdAt(LocalDateTime.now())
                .build();

        Feature1Entity saved = repository.save(entity);
        return mapToDTO(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public Feature1ResponseDTO getFeature1ById(Long id) {
        Feature1Entity entity = repository.findById(id)
                .orElseThrow(() -> new ApiException("Feature1 record not found with id: " + id, HttpStatus.NOT_FOUND));
        return mapToDTO(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Feature1ResponseDTO> getAllFeature1() {
        return repository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private Feature1ResponseDTO mapToDTO(Feature1Entity entity) {
        return Feature1ResponseDTO.builder()
                .id(entity.getId())
                .username(entity.getUsername())
                .email(entity.getEmail())
                .status(entity.getStatus())
                .formattedCreatedAt(DateTimeUtils.formatLocalDateTime(entity.getCreatedAt()))
                .build();
    }
}
