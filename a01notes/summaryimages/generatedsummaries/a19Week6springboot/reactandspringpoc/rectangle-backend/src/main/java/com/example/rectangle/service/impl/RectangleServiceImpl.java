package com.example.rectangle.service.impl; // Package syntax: Declares service implementation package namespace

import com.example.rectangle.dto.PAResponseDto; // Import syntax: Imports PA output response DTO
import com.example.rectangle.dto.RectangleRequestDto; // Import syntax: Imports Rectangle input request DTO
import com.example.rectangle.exception.ZeroDimensionsException; // Import syntax: Imports custom exception
import com.example.rectangle.service.RectangleService; // Import syntax: Imports service interface
import org.springframework.stereotype.Service; // Import syntax: Imports @Service stereotype annotation

/**
 * Service implementation containing calculation logic.
 */
@Service // Syntax: Marks class as Spring Service component bean
public class RectangleServiceImpl implements RectangleService { // Class syntax: Implements RectangleService interface

    public RectangleServiceImpl() { // Constructor syntax: Default constructor
        // No initialization required for this service
        System.out.println("constructor of RectangleServiceImpl called");
    }


    @Override // Syntax: Method override annotation
    public PAResponseDto calculatePA(RectangleRequestDto rectangleDto) { // Method syntax: Accepts DTO, returns PAResponseDto
        double length = rectangleDto.getLength(); // Syntax: Extracts length double value from request DTO
        double breadth = rectangleDto.getBreadth(); // Syntax: Extracts breadth double value from request DTO

        // Conditional Syntax: Checks if length AND breadth are both zero
        if (length == 0 && breadth == 0) { // If condition syntax: Logical AND operator (&&)
            throw new ZeroDimensionsException("Length and Breadth cannot both be zero!"); // Throw syntax: Instantiates and throws custom exception
        }

        // Calculation Syntax
        double area = length * breadth; // Arithmetic syntax: Multiplication operator (*) calculates Area
        double perimeter = 2 * (length + breadth); // Arithmetic syntax: Addition (+) and Multiplication (*) calculates Perimeter

        return new PAResponseDto(perimeter, area); // Return syntax: Instantiates and returns PAResponseDto output object
    }
}
