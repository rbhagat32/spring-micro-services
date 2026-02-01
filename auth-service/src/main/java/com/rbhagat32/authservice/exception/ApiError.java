package com.rbhagat32.authservice.exception;

import org.springframework.http.HttpStatus;

import java.time.Instant;

public record ApiError(
        Instant timeStamp,
        HttpStatus statusCode,
        String error,
        String message,
        String path) {
    
}