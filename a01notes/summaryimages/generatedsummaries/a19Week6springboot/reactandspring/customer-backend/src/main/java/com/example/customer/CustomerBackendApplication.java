package com.example.customer; // Declares the package namespace for the main entry point

import org.springframework.boot.SpringApplication; // Import SpringApplication class to bootstrap the Spring Boot app
import org.springframework.boot.autoconfigure.SpringBootApplication; // Import annotation that enables auto-configuration and component scanning

/**
 * Main application class for Spring Boot backend.
 * @SpringBootApplication combines @Configuration, @EnableAutoConfiguration, and @ComponentScan.
 */
@SpringBootApplication // Marks this class as the Spring Boot entry point and enables automatic configuration
public class CustomerBackendApplication {

    /**
     * Java main method - execution starting point.
     * @param args Command line arguments passed to application.
     */
    public static void main(String[] args) {
        // Launches the Spring application container and starts embedded Tomcat web server on port 8080
        SpringApplication.run(CustomerBackendApplication.class, args);
    }
}
