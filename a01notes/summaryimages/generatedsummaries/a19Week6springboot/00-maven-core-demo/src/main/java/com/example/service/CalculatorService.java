package com.example.service;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Service class performing core calculations and string manipulations.
 * Demonstrates the use of compile-scope dependencies (Apache Commons Lang3 & SLF4J).
 */
public class CalculatorService {

    private static final Logger logger = LoggerFactory.getLogger(CalculatorService.class);

    public int add(int a, int b) {
        logger.info("Performing addition: {} + {}", a, b);
        return a + b;
    }

    public int subtract(int a, int b) {
        logger.info("Performing subtraction: {} - {}", a, b);
        return a - b;
    }

    public int multiply(int a, int b) {
        logger.info("Performing multiplication: {} * {}", a, b);
        return a * b;
    }

    public double divide(int a, int b) {
        logger.info("Performing division: {} / {}", a, b);
        if (b == 0) {
            logger.error("Attempted division by zero!");
            throw new IllegalArgumentException("Cannot divide by zero");
        }
        return (double) a / b;
    }

    public String formatMessage(String rawInput) {
        logger.info("Formatting raw input using Commons Lang3 StringUtils");
        if (StringUtils.isBlank(rawInput)) {
            return "EMPTY_INPUT";
        }
        return StringUtils.upperCase(rawInput.trim());
    }
}
