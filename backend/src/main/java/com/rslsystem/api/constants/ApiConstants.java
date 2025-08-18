package com.rslsystem.api.constants;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class ApiConstants {

  public static final String STATUS = "status";
  public static final String MESSAGE = "message";
  public static final String TIMESTAMP = "timestamp";
  public static final String SERVICE = "service";
  public static final String VERSION = "version";
  public static final String DESCRIPTION = "description";
  public static final String CORS_STATUS = "cors_status";
  public static final String RECEIVED_DATA = "received_data";
  public static final String FRONTEND_CAN_ACCESS = "frontend_can_access";

  // Response Values
  public static final String STATUS_UP = "UP";
  public static final String STATUS_DOWN = "DOWN";
  public static final String CORS_OK = "OK";

  // Valores dinâmicos dos profiles (injetados pelo Spring)
  @Value("${rsl.system.name}")
  private String serviceName;

  @Value("${rsl.system.version}")
  private String serviceVersion;

  @Value("${rsl.system.description}")
  private String serviceDescription;
}
