package com.example.backend.features.feature5.controller;

import com.example.backend.features.feature5.dto.Feature5RequestDTO;
import com.example.backend.features.feature5.dto.Feature5ResponseDTO;
import com.example.backend.features.feature5.service.Feature5Service;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Feature 5 REST Controller (Developer 5).
 * Maps endpoints under /api/v1/feature5
 */
@RestController
@RequestMapping("/api/v1/feature5")
@RequiredArgsConstructor
public class Feature5Controller {

    private final Feature5Service service;

    @PostMapping
    public ResponseEntity<Feature5ResponseDTO> recordMetric(@Valid @RequestBody Feature5RequestDTO request) {
        Feature5ResponseDTO response = service.recordMetric(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Feature5ResponseDTO> getMetricById(@PathVariable Long id) {
        Feature5ResponseDTO response = service.getMetricById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<Feature5ResponseDTO>> getMetricsByCategory(@PathVariable String category) {
        List<Feature5ResponseDTO> response = service.getMetricsByCategory(category);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<Feature5ResponseDTO>> getAllMetrics() {
        List<Feature5ResponseDTO> response = service.getAllMetrics();
        return ResponseEntity.ok(response);
    }
}
