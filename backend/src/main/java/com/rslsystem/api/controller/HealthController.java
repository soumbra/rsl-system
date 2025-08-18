package com.rslsystem.api.controller;

import com.rslsystem.api.constants.ApiConstants;
import com.rslsystem.api.dto.ResponseWrapper;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HealthController {

  private final ApiConstants apiConstants;

  public HealthController(ApiConstants apiConstants) {
    this.apiConstants = apiConstants;
  }

  @GetMapping("/health")
  public ResponseEntity<ResponseWrapper<Map<String, Object>>> health() {
    Map<String, Object> healthData = new HashMap<>();
    healthData.put(ApiConstants.SERVICE, apiConstants.getServiceName());
    healthData.put(ApiConstants.VERSION, apiConstants.getServiceVersion());

    ResponseWrapper<Map<String, Object>> response = ResponseWrapper.success(healthData);
    return ResponseEntity.ok(response);
  }

  @GetMapping("/info")
  public ResponseEntity<ResponseWrapper<Map<String, Object>>> info() {
    Map<String, Object> infoData = new HashMap<>();
    infoData.put("name", "RSL System - Sistema de Revisões Sistemáticas");
    infoData.put(ApiConstants.DESCRIPTION, apiConstants.getServiceDescription());
    infoData.put("java_version", System.getProperty("java.version"));
    infoData.put("spring_boot", "3.x");

    ResponseWrapper<Map<String, Object>> response = ResponseWrapper.success(infoData);
    return ResponseEntity.ok(response);
  }
}
