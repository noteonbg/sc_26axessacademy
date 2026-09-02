package com.example.apitestingpoc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main Spring Boot Application entry point for API Testing POC.
 * 
 * @SpringBootApplication marks this as the primary configuration class
 * and enables auto-configuration, component scanning, and property support.
 */
@SpringBootApplication
public class ApiTestingPocApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiTestingPocApplication.class, args);
    }
}
