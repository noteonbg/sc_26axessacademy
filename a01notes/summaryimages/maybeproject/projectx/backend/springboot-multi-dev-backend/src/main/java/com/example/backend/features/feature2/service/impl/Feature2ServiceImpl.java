package com.example.backend.features.feature2.service.impl;

import com.example.backend.common.exception.ApiException;
import com.example.backend.common.util.DateTimeUtils;
import com.example.backend.features.feature2.dto.Feature2RequestDTO;
import com.example.backend.features.feature2.dto.Feature2ResponseDTO;
import com.example.backend.features.feature2.entity.Feature2Entity;
import com.example.backend.features.feature2.repository.Feature2Repository;
import com.example.backend.features.feature2.service.Feature2Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Feature 2 Implementation (Developer 2).
 */
@Service
@RequiredArgsConstructor
public class Feature2ServiceImpl implements Feature2Service {

    private final Feature2Repository repository;

    @Override
    @Transactional
    public Feature2ResponseDTO createFeature2(Feature2RequestDTO request) {
        Feature2Entity entity = Feature2Entity.builder()
                .name(request.getName())
                .price(request.getPrice())
                .stockQuantity(request.getStockQuantity())
                .createdAt(LocalDateTime.now())
                .build();

        Feature2Entity saved = repository.save(entity);
        return mapToDTO(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public Feature2ResponseDTO getFeature2ById(Long id) {
        Feature2Entity entity = repository.findById(id)
                .orElseThrow(() -> new ApiException("Feature2 catalog item not found with id: " + id, HttpStatus.NOT_FOUND));
        return mapToDTO(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Feature2ResponseDTO> getAllFeature2() {
        return repository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Feature2ResponseDTO> searchFeature2(String keyword) {
        return repository.findByNameContainingIgnoreCase(keyword).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private Feature2ResponseDTO mapToDTO(Feature2Entity entity) {
        return Feature2ResponseDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .price(entity.getPrice())
                .stockQuantity(entity.getStockQuantity())
                .formattedCreatedAt(DateTimeUtils.formatLocalDateTime(entity.getCreatedAt()))
                .build();
    }
}
