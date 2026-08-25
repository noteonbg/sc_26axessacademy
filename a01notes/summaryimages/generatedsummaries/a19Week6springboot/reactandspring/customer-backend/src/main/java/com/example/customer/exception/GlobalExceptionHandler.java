package com.example.customer.exception; // Declares exception package namespace

import org.springframework.http.HttpStatus; // Enum representing HTTP Status codes
import org.springframework.http.ResponseEntity; // Wrapper for HTTP response body and status code
import org.springframework.validation.FieldError; // Spring validation class for field-specific errors
import org.springframework.web.bind.MethodArgumentNotValidException; // Exception thrown when @Valid fails
import org.springframework.web.bind.annotation.ExceptionHandler; // Annotation declaring exception handler methods
import org.springframework.web.bind.annotation.RestControllerAdvice; // Annotation for global REST controller advice

import java.util.HashMap; // HashMap implementation for storing field validation errors
import java.util.Map; // Map interface for key-value error dictionary

/**
 * Global exception handler intercepting exceptions thrown across all REST controllers.
 * Returns ResponseEntity<ErrorResponse> with appropriate HTTP Status Codes (404, 400, 500).
 */
@RestControllerAdvice // Intercepts exceptions across all @RestController classes and formats output as JSON
public class GlobalExceptionHandler {

    /**
     * Handles CustomerNotFoundException and returns HTTP 404 NOT FOUND.
     */
    @ExceptionHandler(CustomerNotFoundException.class) // Specifies target exception to catch
    public ResponseEntity<ErrorResponse> handleCustomerNotFoundException(CustomerNotFoundException ex) {
        // Construct structured ErrorResponse object
        ErrorResponse error = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(), // 404 integer status code
                HttpStatus.NOT_FOUND.getReasonPhrase(), // "Not Found" title phrase
                ex.getMessage() // Exception detail message
        );
        // Return ResponseEntity with 404 status code
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    /**
     * Handles Bean Validation failures (@Valid) and returns HTTP 400 BAD REQUEST.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class) // Catches Spring DTO validation errors
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>(); // Create map to hold field -> error message pairs

        // Iterate through all validation errors extracted from BindingResult
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField(); // Extract invalid field name (e.g. "email")
            String errorMessage = error.getDefaultMessage(); // Extract validation rule message
            errors.put(fieldName, errorMessage); // Store pair in errors map
        });

        // Construct structured ErrorResponse object containing validation details map
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(), // 400 integer status code
                HttpStatus.BAD_REQUEST.getReasonPhrase(), // "Bad Request" title phrase
                "Input validation failed", // General summary message
                errors // Field errors dictionary
        );
        // Return ResponseEntity with 400 status code
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles IllegalArgumentException and returns HTTP 400 BAD REQUEST.
     */
    @ExceptionHandler(IllegalArgumentException.class) // Catches invalid arguments thrown in business logic
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
        ErrorResponse error = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(), // 400 status code
                HttpStatus.BAD_REQUEST.getReasonPhrase(), // "Bad Request" phrase
                ex.getMessage() // Exception message
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    /**
     * Fallback handler catching all uncaught exceptions and returning HTTP 500 INTERNAL SERVER ERROR.
     */
    @ExceptionHandler(Exception.class) // Generic catch-all exception handler
    public ResponseEntity<ErrorResponse> handleGlobalException(Exception ex) {
        ErrorResponse error = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(), // 500 status code
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), // "Internal Server Error" phrase
                "An unexpected error occurred: " + ex.getMessage() // Error message
        );
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
