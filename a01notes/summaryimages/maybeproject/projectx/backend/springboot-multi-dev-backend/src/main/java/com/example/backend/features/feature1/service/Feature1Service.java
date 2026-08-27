package com.example.backend.features.feature1.service;

import com.example.backend.features.feature1.dto.Feature1RequestDTO;
import com.example.backend.features.feature1.dto.Feature1ResponseDTO;

import java.util.List;

/**
 * Feature 1 Public Service Interface (Developer 1).
 * Other feature modules can call methods in this interface if necessary.
 */
public interface Feature1Service {
    Feature1ResponseDTO createFeature1(Feature1RequestDTO request);
    Feature1ResponseDTO getFeature1ById(Long id);
    List<Feature1ResponseDTO> getAllFeature1();
}
