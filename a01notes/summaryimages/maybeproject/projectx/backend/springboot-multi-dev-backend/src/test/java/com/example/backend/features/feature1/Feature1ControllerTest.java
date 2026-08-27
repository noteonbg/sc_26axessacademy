package com.example.backend.features.feature1;

import com.example.backend.features.feature1.controller.Feature1Controller;
import com.example.backend.features.feature1.dto.Feature1ResponseDTO;
import com.example.backend.features.feature1.service.Feature1Service;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcStatusResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcStatusResultMatchers.status;

@WebMvcTest(Feature1Controller.class)
class Feature1ControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private Feature1Service service;

    @Test
    @WithMockUser
    void shouldReturnFeature1ById() throws Exception {
        Feature1ResponseDTO mockResponse = Feature1ResponseDTO.builder()
                .id(1L)
                .username("developer1")
                .email("dev1@example.com")
                .status("ACTIVE")
                .formattedCreatedAt("2026-08-26 21:00:00")
                .build();

        Mockito.when(service.getFeature1ById(1L)).thenReturn(mockResponse);

        mockMvc.perform(get("/api/v1/feature1/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("developer1"))
                .andExpect(jsonPath("$.email").value("dev1@example.com"));
    }
}
