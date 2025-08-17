package com.rslsystem.api.exception;

import com.rslsystem.api.dto.ResponseWrapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseWrapper<Object>> handleGenericException(
            Exception ex, WebRequest request) {
        
        ResponseWrapper<Object> response = ResponseWrapper.error(
            "ERROR", 
            "Erro interno do servidor", 
            ex.getMessage()
        );
        
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ResponseWrapper<Object>> handleNotFound(
            NoResourceFoundException ex, WebRequest request) {
        
        ResponseWrapper<Object> response = ResponseWrapper.error(
            "NOT_FOUND",
            "Recurso não encontrado",
            ex.getResourcePath()
        );
        
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ResponseWrapper<Object>> handleRuntimeException(
            RuntimeException ex, WebRequest request) {
        
        ResponseWrapper<Object> response = ResponseWrapper.error(
            "ERROR",
            "Erro de execução", 
            ex.getMessage()
        );
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}