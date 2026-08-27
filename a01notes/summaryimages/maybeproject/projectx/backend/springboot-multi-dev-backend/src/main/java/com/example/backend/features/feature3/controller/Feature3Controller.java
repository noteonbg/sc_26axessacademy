package com.example.backend.features.feature3.controller;

import com.example.backend.features.feature3.dto.Feature3RequestDTO;
import com.example.backend.features.feature3.dto.Feature3ResponseDTO;
import com.example.backend.features.feature3.service.Feature3Service;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Feature 3 REST Controller (Developer 3).
 * Maps endpoints under /api/v1/feature3
 */
@RestController
@RequestMapping("/api/v1/feature3")
@RequiredArgsConstructor
public class Feature3Controller {

    private final Feature3Service service;

    @PostMapping
    public ResponseEntity<Feature3ResponseDTO> placeOrder(@Valid @RequestBody Feature3RequestDTO request) {
        Feature3ResponseDTO response = service.placeOrder(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Feature3ResponseDTO> getOrderById(@PathVariable Long id) {
        Feature3ResponseDTO response = service.getOrderById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Feature3ResponseDTO>> getOrdersByUserId(@PathVariable Long userId) {
        List<Feature3ResponseDTO> response = service.getOrdersByUserId(userId);
        return ResponseEntity.ok(response);
    }
}
