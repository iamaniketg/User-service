package com.Aniket.User_service.exceptions;

import com.Aniket.User_service.utils.ExceptionCounter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    private final ExceptionCounter exceptionCounter;

    public GlobalExceptionHandler(ExceptionCounter exceptionCounter) {
        this.exceptionCounter = exceptionCounter;
    }

    @ExceptionHandler(RuntimeException.class)
    public void handleException(Exception ex) {
        // Increment Prometheus counter
        exceptionCounter.increment();

        // (You can also log or return a response here)
    }
}