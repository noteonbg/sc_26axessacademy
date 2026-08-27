package com.example.backend.features.feature4.controller;

import com.example.backend.features.feature4.dto.Feature4RequestDTO;
import com.example.backend.features.feature4.dto.Feature4ResponseDTO;
import com.example.backend.features.feature4.service.Feature4Service;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Feature 4 REST Controller (Developer 4).
 * Maps endpoints under /api/v1/feature4
 */
@RestController
@RequestMapping("/api/v1/feature4")
@RequiredArgsConstructor
public class Feature4Controller {

    private final Feature4Service service;

    @PostMapping
    public ResponseEntity<Feature4ResponseDTO> processPayment(@Valid @RequestBody Feature4RequestDTO request) {
        Feature4ResponseDTO response = service.processPayment(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Feature4ResponseDTO> getPaymentById(@PathVariable Long id) {
        Feature4ResponseDTO response = service.getPaymentById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<Feature4ResponseDTO> getPaymentByOrderId(@PathVariable Long orderId) {
        Feature4ResponseDTO response = service.getPaymentByOrderId(orderId);
        return ResponseEntity.ok(response);
    }
}
