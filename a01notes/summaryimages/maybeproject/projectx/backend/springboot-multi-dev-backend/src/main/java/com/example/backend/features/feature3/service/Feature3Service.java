package com.example.backend.features.feature3.service;

import com.example.backend.features.feature3.dto.Feature3RequestDTO;
import com.example.backend.features.feature3.dto.Feature3ResponseDTO;

import java.util.List;

/**
 * Feature 3 Service Interface (Developer 3).
 */
public interface Feature3Service {
    Feature3ResponseDTO placeOrder(Feature3RequestDTO request);
    Feature3ResponseDTO getOrderById(Long id);
    List<Feature3ResponseDTO> getOrdersByUserId(Long userId);
}
