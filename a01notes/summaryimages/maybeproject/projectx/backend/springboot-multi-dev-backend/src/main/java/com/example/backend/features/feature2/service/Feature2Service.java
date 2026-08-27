package com.example.backend.features.feature2.service;

import com.example.backend.features.feature2.dto.Feature2RequestDTO;
import com.example.backend.features.feature2.dto.Feature2ResponseDTO;

import java.util.List;

/**
 * Feature 2 Public Service Interface (Developer 2).
 */
public interface Feature2Service {
    Feature2ResponseDTO createFeature2(Feature2RequestDTO request);
    Feature2ResponseDTO getFeature2ById(Long id);
    List<Feature2ResponseDTO> getAllFeature2();
    List<Feature2ResponseDTO> searchFeature2(String keyword);
}
