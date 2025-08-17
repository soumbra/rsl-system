package com.rslsystem.api.controller;

import com.rslsystem.api.constants.ApiConstants;
import com.rslsystem.api.dto.ResponseWrapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/test")
public class TestController {

    @GetMapping
    public ResponseEntity<ResponseWrapper<Map<String, Object>>> testGet() {
        Map<String, Object> testData = new HashMap<>();
        testData.put(ApiConstants.MESSAGE, "GET request funcionando!");
        testData.put(ApiConstants.CORS_STATUS, ApiConstants.CORS_OK);
        
        ResponseWrapper<Map<String, Object>> response = ResponseWrapper.success(testData);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ResponseWrapper<Map<String, Object>>> testPost(@RequestBody Map<String, Object> payload) {
        Map<String, Object> testData = new HashMap<>();
        testData.put(ApiConstants.MESSAGE, "POST request funcionando!");
        testData.put(ApiConstants.RECEIVED_DATA, payload);
        testData.put(ApiConstants.CORS_STATUS, ApiConstants.CORS_OK);
        
        ResponseWrapper<Map<String, Object>> response = ResponseWrapper.success(testData);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/cors")
    public ResponseEntity<ResponseWrapper<Map<String, Object>>> testCors() {
        Map<String, Object> corsData = new HashMap<>();
        corsData.put(ApiConstants.MESSAGE, "CORS configurado corretamente!");
        corsData.put(ApiConstants.FRONTEND_CAN_ACCESS, true);
        
        ResponseWrapper<Map<String, Object>> response = ResponseWrapper.success(corsData);
        return ResponseEntity.ok(response);
    }
}