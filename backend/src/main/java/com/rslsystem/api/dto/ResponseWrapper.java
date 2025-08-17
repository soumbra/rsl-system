package com.rslsystem.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.rslsystem.api.constants.ApiConstants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseWrapper<T> {
    
    private String status;
    private String message;
    private LocalDateTime timestamp;
    private T data;
    private Object error;
    
    // Métodos estáticos para criar respostas de sucesso
    public static <T> ResponseWrapper<T> success(T data) {
        return ResponseWrapper.<T>builder()
                .status(ApiConstants.STATUS_UP)
                .timestamp(LocalDateTime.now())
                .data(data)
                .build();
    }
    
    public static <T> ResponseWrapper<T> success(String message, T data) {
        return ResponseWrapper.<T>builder()
                .status(ApiConstants.STATUS_UP)
                .message(message)
                .timestamp(LocalDateTime.now())
                .data(data)
                .build();
    }
    
    // Métodos estáticos para criar respostas de erro
    public static <T> ResponseWrapper<T> error(String message) {
        return ResponseWrapper.<T>builder()
                .status("ERROR")
                .message(message)
                .timestamp(LocalDateTime.now())
                .build();
    }
    
    public static <T> ResponseWrapper<T> error(String status, String message, Object error) {
        return ResponseWrapper.<T>builder()
                .status(status)
                .message(message)
                .timestamp(LocalDateTime.now())
                .error(error)
                .build();
    }
}