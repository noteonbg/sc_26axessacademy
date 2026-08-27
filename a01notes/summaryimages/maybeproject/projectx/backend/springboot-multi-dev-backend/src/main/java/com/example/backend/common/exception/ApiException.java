package com.example.backend.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Custom Base API Exception used across all feature modules.
 */
@Getter
public class ApiException extends RuntimeException {
    private final HttpStatus status;

    public ApiException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}
