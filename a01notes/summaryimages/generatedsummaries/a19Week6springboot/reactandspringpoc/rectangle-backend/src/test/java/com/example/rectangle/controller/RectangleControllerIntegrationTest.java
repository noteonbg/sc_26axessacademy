package com.example.rectangle.controller;

import com.example.rectangle.dto.RectangleRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Integration Test for Rectangle API verifying full Spring Boot HTTP request-response flow.
 */
@SpringBootTest
@AutoConfigureMockMvc
class RectangleControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Integration Test: POST /api/rectangle/calculate with valid payload returns HTTP 200 OK")
    void testCalculatePAApiSuccess() throws Exception {
        RectangleRequestDto request = new RectangleRequestDto(10.0, 5.0);

        mockMvc.perform(post("/api/rectangle/calculate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.area", is(50.0)))
                .andExpect(jsonPath("$.perimeter", is(30.0)));
    }

    @Test
    @DisplayName("Integration Test: POST /api/rectangle/calculate with L=0 & B=0 returns HTTP 400 Bad Request")
    void testCalculatePAApiZeroDimensionsException() throws Exception {
        RectangleRequestDto request = new RectangleRequestDto(0.0, 0.0);

        mockMvc.perform(post("/api/rectangle/calculate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("Length and Breadth cannot both be zero!")));
    }
}
