package com.standardchartered.controllerdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*
 * @SpringBootApplication Annotation Syntax Explanation:
 * This annotation combines three core Spring Boot annotations:
 * 1. @Configuration: Marks this class as a source of bean definitions for the application context.
 * 2. @EnableAutoConfiguration: Tells Spring Boot to automatically configure beans based on classpath dependencies (e.g. embedded Tomcat, Jackson JSON).
 * 3. @ComponentScan: Directs Spring to scan this package (and sub-packages) for components, controllers, and services.
 */
@SpringBootApplication
public class ControllerDemoApplication {
    public static void main(String[] args) {
        // Boots Spring ApplicationContext, starts embedded Tomcat on port 8081
        SpringApplication.run(ControllerDemoApplication.class, args);
    }
}
