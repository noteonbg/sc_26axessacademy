package com.example.rectangle.service;

import com.example.rectangle.dto.PAResponseDto;
import com.example.rectangle.dto.RectangleRequestDto;
import com.example.rectangle.exception.ZeroDimensionsException;
import com.example.rectangle.service.impl.RectangleServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit Test for RectangleServiceImpl verifying calculation logic and custom exceptions.
 */
class RectangleServiceImplTest {

    private RectangleServiceImpl rectangleService;

    @BeforeEach
    void setUp() {
        rectangleService = new RectangleServiceImpl();
    }

    @Test
    @DisplayName("Unit Test: Calculate Perimeter and Area for L=5.0 and B=4.0")
    void testCalculatePASuccess() {
        // Given: Input DTO with length=5.0 and breadth=4.0
        RectangleRequestDto requestDto = new RectangleRequestDto(5.0, 4.0);

        // When: Calculating PA
        PAResponseDto response = rectangleService.calculatePA(requestDto);

        // Then: Area should be 20.0 (5 * 4), Perimeter should be 18.0 (2 * (5 + 4))
        assertNotNull(response);
        assertEquals(20.0, response.getArea(), 0.001);
        assertEquals(18.0, response.getPerimeter(), 0.001);
    }

    @Test
    @DisplayName("Unit Test: Throw ZeroDimensionsException when both L=0 and B=0")
    void testCalculatePAZeroDimensionsException() {
        // Given: Input DTO with length=0.0 and breadth=0.0
        RectangleRequestDto requestDto = new RectangleRequestDto(0.0, 0.0);

        // Then: Should throw ZeroDimensionsException
        ZeroDimensionsException exception = assertThrows(
                ZeroDimensionsException.class,
                () -> rectangleService.calculatePA(requestDto)
        );

        assertEquals("Length and Breadth cannot both be zero!", exception.getMessage());
    }
}
