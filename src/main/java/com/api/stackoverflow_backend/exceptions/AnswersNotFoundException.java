package com.api.stackoverflow_backend.exceptions;

public class AnswersNotFoundException extends RuntimeException {

    public AnswersNotFoundException(String message) {
        super(message);
    }
    
}
