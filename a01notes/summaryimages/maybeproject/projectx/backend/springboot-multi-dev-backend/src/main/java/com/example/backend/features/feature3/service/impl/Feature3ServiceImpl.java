package com.example.backend.features.feature3.service.impl;

import com.example.backend.common.exception.ApiException;
import com.example.backend.common.util.DateTimeUtils;
import com.example.backend.features.feature2.dto.Feature2ResponseDTO;
import com.example.backend.features.feature2.service.Feature2Service;
import com.example.backend.features.feature3.dto.Feature3RequestDTO;
import com.example.backend.features.feature3.dto.Feature3ResponseDTO;
import com.example.backend.features.feature3.entity.Feature3Entity;
import com.example.backend.features.feature3.repository.Feature3Repository;
import com.example.backend.features.feature3.service.Feature3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Feature 3 Implementation (Developer 3).
 * Demonstrates clean inter-feature integration with Developer 2's Feature2Service interface.
 */
@Service
@RequiredArgsConstructor
public class Feature3ServiceImpl implements Feature3Service {

    private final Feature3Repository repository;
    private final Feature2Service feature2Service; // Inter-feature integration via public interface

    @Override
    @Transactional
    public Feature3ResponseDTO placeOrder(Feature3RequestDTO request) {
        // Fetch catalog item price from Developer 2's feature module
        Feature2ResponseDTO catalogItem = feature2Service.getFeature2ById(request.getCatalogItemId());

        BigDecimal calculatedTotal = catalogItem.getPrice().multiply(BigDecimal.valueOf(request.getQuantity()));

        Feature3Entity entity = Feature3Entity.builder()
                .userId(request.getUserId())
                .catalogItemId(request.getCatalogItemId())
                .quantity(request.getQuantity())
                .totalPrice(calculatedTotal)
                .status("PENDING")
                .createdAt(LocalDateTime.now())
                .build();

        Feature3Entity saved = repository.save(entity);
        return mapToDTO(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public Feature3ResponseDTO getOrderById(Long id) {
        Feature3Entity entity = repository.findById(id)
                .orElseThrow(() -> new ApiException("Feature3 order not found with id: " + id, HttpStatus.NOT_FOUND));
        return mapToDTO(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Feature3ResponseDTO> getOrdersByUserId(Long userId) {
        return repository.findByUserId(userId).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private Feature3ResponseDTO mapToDTO(Feature3Entity entity) {
        return Feature3ResponseDTO.builder()
                .id(entity.getId())
                .userId(entity.getUserId())
                .catalogItemId(entity.getCatalogItemId())
                .quantity(entity.getQuantity())
                .totalPrice(entity.getTotalPrice())
                .status(entity.getStatus())
                .formattedCreatedAt(DateTimeUtils.formatLocalDateTime(entity.getCreatedAt()))
                .build();
    }
}
