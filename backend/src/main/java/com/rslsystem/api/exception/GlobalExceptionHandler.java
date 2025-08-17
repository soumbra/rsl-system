package com.rslsystem.api.exception;

import com.rslsystem.api.constants.ApiConstants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGenericException(
            Exception ex, WebRequest request) {
        
        Map<String, Object> response = new HashMap<>();
        response.put(ApiConstants.STATUS, "ERROR");
        response.put(ApiConstants.MESSAGE, "Erro interno do servidor");
        response.put(ApiConstants.TIMESTAMP, LocalDateTime.now());
        response.put("error", ex.getMessage());
        
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNotFound(
            NoResourceFoundException ex, WebRequest request) {
        
        Map<String, Object> response = new HashMap<>();
        response.put(ApiConstants.STATUS, "NOT_FOUND");
        response.put(ApiConstants.MESSAGE, "Recurso não encontrado");
        response.put(ApiConstants.TIMESTAMP, LocalDateTime.now());
        response.put("path", ex.getResourcePath());
        
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, Object>> handleRuntimeException(
            RuntimeException ex, WebRequest request) {
        
        Map<String, Object> response = new HashMap<>();
        response.put(ApiConstants.STATUS, "ERROR");
        response.put(ApiConstants.MESSAGE, "Erro de execução");
        response.put(ApiConstants.TIMESTAMP, LocalDateTime.now());
        response.put("error", ex.getMessage());
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}