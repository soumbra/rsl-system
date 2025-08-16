package com.rslsystem.api.controller;

import com.rslsystem.api.constants.ApiConstants;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class HealthController {

    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> health() {
        Map<String, Object> response = new HashMap<>();
        response.put(ApiConstants.STATUS, ApiConstants.STATUS_UP);
        response.put(ApiConstants.TIMESTAMP, LocalDateTime.now());
        response.put(ApiConstants.SERVICE, ApiConstants.SERVICE_NAME);
        response.put(ApiConstants.VERSION, ApiConstants.SERVICE_VERSION);
        
        return ResponseEntity.ok(response);
    }

    @GetMapping("/info")
    public ResponseEntity<Map<String, Object>> info() {
        Map<String, Object> response = new HashMap<>();
        response.put("name", "RSL System - Sistema de Revisões Sistemáticas");
        response.put(ApiConstants.DESCRIPTION, ApiConstants.SERVICE_DESCRIPTION);
        response.put("java_version", System.getProperty("java.version"));
        response.put("spring_boot", "3.x");
        
        return ResponseEntity.ok(response);
    }
}