package com.standardchartered.controllerdemo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.net.URI;

/*
 * SYNTAX COMMENTARY: Centralized Global Exception Handler
 *
 * @ControllerAdvice:
 * - Tells Spring Boot that this class intercepts exceptions thrown by any @RestController handler method across the application.
 * - Promotes clean code by removing repetitive try-catch blocks inside individual controllers.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /*
     * SYNTAX COMMENTARY: Handling Specific Exception Types
     *
     * @ExceptionHandler(CustomerNotFoundException.class):
     * - Binds this method to handle instances of CustomerNotFoundException thrown anywhere in the Web Layer.
     *
     * ProblemDetail (RFC 7807 Standard):
     * - Introduced in Spring Boot 3 / Java 17+ to return standardized HTTP error payloads containing status, title, detail, and URI type.
     */
    @ExceptionHandler(CustomerNotFoundException.class)
    public ProblemDetail handleCustomerNotFound(CustomerNotFoundException ex) {
        // Creates RFC 7807 problem detail with HTTP 404 Status Code
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(
                HttpStatus.NOT_FOUND,
                ex.getMessage()
        );
        problem.setTitle("Bank Customer Not Found");
        problem.setType(URI.create("https://bank.sc.com/errors/customer-not-found"));
        return problem;
    }
}
