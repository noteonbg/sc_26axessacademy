package com.example.rectangle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//we are X, we are using License given by somebody in this case spring boot
/**
 * Entry point for Rectangle Calculation Spring Boot backend API.
 */
@SpringBootApplication
public class RectangleBackendApplication {

    public static void main(String[] args) {
        
        System.out.println("horror");
        SpringApplication.run(RectangleBackendApplication.class, args);

        //hey spring, annotation i have used on tihs class.
    }
}
