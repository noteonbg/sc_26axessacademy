package com.example.rectangle.controller; // Package syntax: Declares controller package namespace

import com.example.rectangle.dto.PAResponseDto; // Import syntax: Imports PA output response DTO
import com.example.rectangle.dto.RectangleRequestDto; // Import syntax: Imports Rectangle input request DTO
import com.example.rectangle.service.RectangleService; // Import syntax: Imports Rectangle service interface
import jakarta.validation.Valid; // Import syntax: Imports @Valid annotation for automatic DTO validation
import org.springframework.http.HttpStatus; // Import syntax: Imports HttpStatus enum for HTTP status codes
import org.springframework.http.ResponseEntity; // Import syntax: Imports ResponseEntity wrapper class
import org.springframework.web.bind.annotation.CrossOrigin; // Import syntax: Imports @CrossOrigin annotation
import org.springframework.web.bind.annotation.PostMapping; // Import syntax: Imports @PostMapping annotation
import org.springframework.web.bind.annotation.RequestBody; // Import syntax: Imports @RequestBody annotation
import org.springframework.web.bind.annotation.RequestMapping; // Import syntax: Imports @RequestMapping annotation
import org.springframework.web.bind.annotation.RestController; // Import syntax: Imports @RestController annotation

/**
 * REST Controller for Rectangle PA calculations.

 */
@RequestMapping("/api/rectangle") // Syntax: Defines base URL path for controller endpoints
@CrossOrigin(origins = "*") // Syntax: Enables Cross-Origin Resource Sharing (CORS) for React frontend
@RestController // Syntax: Marks class as RESTful Controller returning JSON HTTP responses
public class RectangleController { // Class syntax: Defines public class RectangleController

    private final RectangleService rectangleService; // Syntax: Private final field for service dependency

    /**
     * Constructor injection syntax.
     */
    public RectangleController(RectangleService rectangleService) { // Constructor syntax
        this.rectangleService = rectangleService; // Syntax: Assigns injected service instance
    }

    /**
     * Endpoint calculating Perimeter and Area.
     */

    //event registration for which event post eventi n http request..
    @PostMapping("/calculate") // Syntax: Maps HTTP POST requests to /api/rectangle/calculate
    public ResponseEntity<PAResponseDto> calculatePA( // Syntax: Method returning ResponseEntity<PAResponseDto>
            @Valid @RequestBody RectangleRequestDto rectangleDto) { // Syntax: @Valid triggers validation, @RequestBody binds JSON to DTO

// took the json and converted that into a RectangleRequestDTO object
        System.out.println("horror");
        PAResponseDto paResponse = rectangleService.calculatePA(rectangleDto); // Syntax: Calls service method to calculate PA
        return new ResponseEntity<>(paResponse, HttpStatus.OK); // Syntax: Returns ResponseEntity with HTTP Status 200 OK
    }
}
