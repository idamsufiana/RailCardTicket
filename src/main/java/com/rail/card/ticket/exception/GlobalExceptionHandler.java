package com.rail.card.ticket.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(TicketException.class)
    public ResponseEntity<?> handleTicketException(TicketException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST) // atau 422
                .body(Map.of(
                        "status", "FAILED",
                        "message", ex.getMessage()
                ));
    }
}
