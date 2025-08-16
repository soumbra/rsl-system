package com.rslsystem.api.controller;

import com.rslsystem.api.constants.ApiConstants;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/test")
public class TestController {

    @GetMapping
    public ResponseEntity<Map<String, Object>> testGet() {
        Map<String, Object> response = new HashMap<>();
        response.put(ApiConstants.MESSAGE, "GET request funcionando!");
        response.put(ApiConstants.TIMESTAMP, LocalDateTime.now());
        response.put(ApiConstants.CORS_STATUS, ApiConstants.CORS_OK);
        
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> testPost(@RequestBody Map<String, Object> payload) {
        Map<String, Object> response = new HashMap<>();
        response.put(ApiConstants.MESSAGE, "POST request funcionando!");
        response.put(ApiConstants.RECEIVED_DATA, payload);
        response.put(ApiConstants.TIMESTAMP, LocalDateTime.now());
        response.put(ApiConstants.CORS_STATUS, ApiConstants.CORS_OK);
        
        return ResponseEntity.ok(response);
    }

    @GetMapping("/cors")
    public ResponseEntity<Map<String, Object>> testCors() {
        Map<String, Object> response = new HashMap<>();
        response.put(ApiConstants.MESSAGE, "CORS configurado corretamente!");
        response.put(ApiConstants.FRONTEND_CAN_ACCESS, true);
        response.put(ApiConstants.TIMESTAMP, LocalDateTime.now());
        
        return ResponseEntity.ok(response);
    }
}