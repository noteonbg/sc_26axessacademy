package com.example.backend.features.feature4.service;

import com.example.backend.features.feature4.dto.Feature4RequestDTO;
import com.example.backend.features.feature4.dto.Feature4ResponseDTO;

/**
 * Feature 4 Service Interface (Developer 4).
 */
public interface Feature4Service {
    Feature4ResponseDTO processPayment(Feature4RequestDTO request);
    Feature4ResponseDTO getPaymentById(Long id);
    Feature4ResponseDTO getPaymentByOrderId(Long orderId);
}
