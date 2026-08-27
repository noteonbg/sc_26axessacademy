package com.example.backend.features.feature1.controller;

import com.example.backend.features.feature1.dto.Feature1RequestDTO;
import com.example.backend.features.feature1.dto.Feature1ResponseDTO;
import com.example.backend.features.feature1.service.Feature1Service;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Feature 1 REST Controller (Developer 1).
 * Maps endpoints under /api/v1/feature1
 */
@RestController
@RequestMapping("/api/v1/feature1")
@RequiredArgsConstructor
public class Feature1Controller {

    private final Feature1Service service;

    @PostMapping
    public ResponseEntity<Feature1ResponseDTO> createFeature1(@Valid @RequestBody Feature1RequestDTO request) {
        Feature1ResponseDTO response = service.createFeature1(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Feature1ResponseDTO> getFeature1ById(@PathVariable Long id) {
        Feature1ResponseDTO response = service.getFeature1ById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<Feature1ResponseDTO>> getAllFeature1() {
        List<Feature1ResponseDTO> response = service.getAllFeature1();
        return ResponseEntity.ok(response);
    }
}
