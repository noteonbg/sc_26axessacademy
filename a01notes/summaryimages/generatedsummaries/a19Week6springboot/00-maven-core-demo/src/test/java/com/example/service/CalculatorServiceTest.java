package com.example.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit Test class for CalculatorService using JUnit 5.
 * Demonstrates Maven test phase execution via maven-surefire-plugin.
 */
class CalculatorServiceTest {

    private CalculatorService calculatorService;

    @BeforeEach
    void setUp() {
        calculatorService = new CalculatorService();
    }

    @Test
    @DisplayName("Test addition of two integers")
    void testAdd() {
        int result = calculatorService.add(10, 20);
        assertEquals(30, result, "10 + 20 should equal 30");
    }

    @Test
    @DisplayName("Test subtraction of two integers")
    void testSubtract() {
        int result = calculatorService.subtract(50, 15);
        assertEquals(35, result, "50 - 15 should equal 35");
    }

    @Test
    @DisplayName("Test multiplication of two integers")
    void testMultiply() {
        int result = calculatorService.multiply(7, 8);
        assertEquals(56, result, "7 * 8 should equal 56");
    }

    @Test
    @DisplayName("Test division of two integers")
    void testDivide() {
        double result = calculatorService.divide(20, 4);
        assertEquals(5.0, result, "20 / 4 should equal 5.0");
    }

    @Test
    @DisplayName("Test division by zero throws IllegalArgumentException")
    void testDivideByZero() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            calculatorService.divide(10, 0);
        });
        assertEquals("Cannot divide by zero", exception.getMessage());
    }

    @Test
    @DisplayName("Test string formatting using Commons Lang3")
    void testFormatMessage() {
        String result = calculatorService.formatMessage("   maven rockstar   ");
        assertEquals("MAVEN ROCKSTAR", result);
    }

    @Test
    @DisplayName("Test string formatting with blank input")
    void testFormatMessageBlank() {
        String result = calculatorService.formatMessage("   ");
        assertEquals("EMPTY_INPUT", result);
    }
}
