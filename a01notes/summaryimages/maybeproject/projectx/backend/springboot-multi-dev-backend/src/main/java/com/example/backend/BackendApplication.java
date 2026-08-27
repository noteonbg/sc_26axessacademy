package com.example.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main Entrypoint for the Multi-Developer Spring Boot Application.
 * 
 * Spring Boot automatically scans all packages under 'com.example.backend',
 * including 'common' and 'features.feature1' through 'features.feature5'.
 */
@SpringBootApplication
public class BackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }
}
