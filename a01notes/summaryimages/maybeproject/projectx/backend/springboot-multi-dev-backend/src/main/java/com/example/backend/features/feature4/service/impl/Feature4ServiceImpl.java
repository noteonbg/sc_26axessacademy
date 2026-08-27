package com.example.backend.features.feature4.service.impl;

import com.example.backend.common.exception.ApiException;
import com.example.backend.common.util.DateTimeUtils;
import com.example.backend.features.feature4.dto.Feature4RequestDTO;
import com.example.backend.features.feature4.dto.Feature4ResponseDTO;
import com.example.backend.features.feature4.entity.Feature4Entity;
import com.example.backend.features.feature4.repository.Feature4Repository;
import com.example.backend.features.feature4.service.Feature4Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Feature 4 Implementation (Developer 4).
 */
@Service
@RequiredArgsConstructor
public class Feature4ServiceImpl implements Feature4Service {

    private final Feature4Repository repository;

    @Override
    @Transactional
    public Feature4ResponseDTO processPayment(Feature4RequestDTO request) {
        String generatedRef = "TXN-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();

        Feature4Entity entity = Feature4Entity.builder()
                .orderId(request.getOrderId())
                .amount(request.getAmount())
                .paymentMethod(request.getPaymentMethod())
                .transactionRef(generatedRef)
                .status("SUCCESS")
                .processedAt(LocalDateTime.now())
                .build();

        Feature4Entity saved = repository.save(entity);
        return mapToDTO(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public Feature4ResponseDTO getPaymentById(Long id) {
        Feature4Entity entity = repository.findById(id)
                .orElseThrow(() -> new ApiException("Feature4 payment record not found with id: " + id, HttpStatus.NOT_FOUND));
        return mapToDTO(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Feature4ResponseDTO getPaymentByOrderId(Long orderId) {
        Feature4Entity entity = repository.findByOrderId(orderId)
                .orElseThrow(() -> new ApiException("Feature4 payment not found for orderId: " + orderId, HttpStatus.NOT_FOUND));
        return mapToDTO(entity);
    }

    private Feature4ResponseDTO mapToDTO(Feature4Entity entity) {
        return Feature4ResponseDTO.builder()
                .id(entity.getId())
                .orderId(entity.getOrderId())
                .amount(entity.getAmount())
                .paymentMethod(entity.getPaymentMethod())
                .transactionRef(entity.getTransactionRef())
                .status(entity.getStatus())
                .formattedProcessedAt(DateTimeUtils.formatLocalDateTime(entity.getProcessedAt()))
                .build();
    }
}
