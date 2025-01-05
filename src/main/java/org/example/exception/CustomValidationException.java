package org.example.exception;

import lombok.Getter;

@Getter
public class CustomValidationException extends RuntimeException {
    private final String errorCode;

    public CustomValidationException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }
}