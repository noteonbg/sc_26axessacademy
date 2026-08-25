package com.example.rectangle.exception; // Package syntax: Declares exception package namespace

import com.example.rectangle.dto.ErrorResponseDto; // Import syntax: Imports error DTO
import org.springframework.http.HttpStatus; // Import syntax: Imports HttpStatus enum
import org.springframework.http.ResponseEntity; // Import syntax: Imports ResponseEntity wrapper
import org.springframework.web.bind.MethodArgumentNotValidException; // Import syntax: Imports validation exception
import org.springframework.web.bind.annotation.ExceptionHandler; // Import syntax: Imports @ExceptionHandler annotation
import org.springframework.web.bind.annotation.RestControllerAdvice; // Import syntax: Imports @RestControllerAdvice annotation

/**
 * Global Exception Handler intercepting exceptions across controllers.
 */
@RestControllerAdvice // Syntax: Marks class as global REST exception interceptor returning JSON error bodies
public class GlobalExceptionHandler { // Class syntax: Defines GlobalExceptionHandler class

    @ExceptionHandler(ZeroDimensionsException.class) // Syntax: @ExceptionHandler catches custom ZeroDimensionsException
    public ResponseEntity<ErrorResponseDto> handleZeroDimensionsException(ZeroDimensionsException ex) { // Handler method syntax
        ErrorResponseDto error = new ErrorResponseDto( // Constructor syntax: Creates ErrorResponseDto instance
                HttpStatus.BAD_REQUEST.value(), // Syntax: Gets integer status 400
                ex.getMessage() // Syntax: Retrieves exception detail message
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST); // Return syntax: Returns ResponseEntity with HTTP 400 status
    }

    @ExceptionHandler(MethodArgumentNotValidException.class) // Syntax: @ExceptionHandler catches DTO validation errors
    public ResponseEntity<ErrorResponseDto> handleValidationException(MethodArgumentNotValidException ex) {
        String firstError = ex.getBindingResult().getAllErrors().get(0).getDefaultMessage(); // Syntax: Extracts validation message
        ErrorResponseDto error = new ErrorResponseDto( // Constructor syntax: Instantiates ErrorResponseDto
                HttpStatus.BAD_REQUEST.value(), // Status 400
                "Validation Error: " + firstError // Error message
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST); // Returns HTTP 400
    }

    @ExceptionHandler(Exception.class) // Syntax: Catch-all @ExceptionHandler for uncaught general exceptions
    public ResponseEntity<ErrorResponseDto> handleGeneralException(Exception ex) {
        ErrorResponseDto error = new ErrorResponseDto(
                HttpStatus.INTERNAL_SERVER_ERROR.value(), // Status 500
                "An error occurred: " + ex.getMessage()
        );
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR); // Returns HTTP 500
    }
}
