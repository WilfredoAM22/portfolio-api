package com.wilfredo.portfolio_api.exception;

import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    public record ValidationErrorResponse(
            String status,
            String message,
            Map<String, String> errors
    ) {}

    public record ServerErrorResponse(
            String status,
            String message
    ) {}


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> handleValidationsExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.merge(error.getField(), error.getDefaultMessage(), (msg1, msg2) -> msg1 + "," + msg2)
        );
        ValidationErrorResponse response = new ValidationErrorResponse(
                "validation_error",
                "Error de validacion en los datos",
                errors
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ServerErrorResponse> handleGeneralException(Exception ex) {
        log.error("Ocurrió un error inesperado en el servidor: ", ex);
        ServerErrorResponse response = new ServerErrorResponse(
                "server_error",
                "Ocurrió un error interno en el servidor. Por favor, inténtalo más tarde."
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}

