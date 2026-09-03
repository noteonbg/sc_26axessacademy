package com.sc.jmeterpoc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * =====================================================================================
 * Standard Chartered Axess Academy - Week 7 Performance Testing POC
 * Main Entry Point: ScEmployeeApplication
 * =====================================================================================
 * 
 * WHY THIS CLASS WAS CREATED:
 * ---------------------------
 * This is the bootstrap class for our Spring Boot application. It initializes the
 * embedded Apache Tomcat HTTP server, scans for Spring components (@RestController, 
 * @Service), and exposes our target REST API on port 3000 so Apache JMeter can 
 * simulate load against it.
 */
@SpringBootApplication
// ^-- @SpringBootApplication enables three core features in one annotation:
//     1. @Configuration: Allows defining beans and environment configurations.
//     2. @EnableAutoConfiguration: Automatically configures Tomcat, Jackson JSON, and MVC.
//     3. @ComponentScan: Automatically discovers and registers @RestController and @Service.
public class ScEmployeeApplication {

    /**
     * Standard Java main() method - the starting point of the JVM runtime.
     *
     * @param args Command-line arguments passed at startup (e.g., --server.port=3000)
     */
    public static void main(String[] args) {
        // Line below launches the Spring Application context, binds Tomcat to port 3000,
        // and starts listening for incoming HTTP connections.
        SpringApplication.run(ScEmployeeApplication.class, args);

        // Friendly console banner to confirm the server status and provide quick-access URLs
        System.out.println("=================================================================");
        System.out.println("  Standard Chartered - SC Employees Service Started (Spring Boot)");
        System.out.println("  Base URL    : http://localhost:3000/sc_employees");
        System.out.println("  Actuator    : http://localhost:3000/actuator/health");
        System.out.println("  Live Stats  : http://localhost:3000/sc_employees/stats");
        System.out.println("  Ready to handle 50 concurrent virtual users from Apache JMeter!");
        System.out.println("=================================================================");
    }
}
