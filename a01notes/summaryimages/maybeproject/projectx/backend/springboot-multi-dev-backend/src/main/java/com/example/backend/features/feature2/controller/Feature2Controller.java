package com.example.backend.features.feature2.controller;

import com.example.backend.features.feature2.dto.Feature2RequestDTO;
import com.example.backend.features.feature2.dto.Feature2ResponseDTO;
import com.example.backend.features.feature2.service.Feature2Service;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Feature 2 REST Controller (Developer 2).
 * Maps endpoints under /api/v1/feature2
 */
@RestController
@RequestMapping("/api/v1/feature2")
@RequiredArgsConstructor
public class Feature2Controller {

    private final Feature2Service service;

    @PostMapping
    public ResponseEntity<Feature2ResponseDTO> createFeature2(@Valid @RequestBody Feature2RequestDTO request) {
        Feature2ResponseDTO response = service.createFeature2(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Feature2ResponseDTO> getFeature2ById(@PathVariable Long id) {
        Feature2ResponseDTO response = service.getFeature2ById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<Feature2ResponseDTO>> getAllFeature2() {
        List<Feature2ResponseDTO> response = service.getAllFeature2();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Feature2ResponseDTO>> searchFeature2(@RequestParam String keyword) {
        List<Feature2ResponseDTO> response = service.searchFeature2(keyword);
        return ResponseEntity.ok(response);
    }
}
