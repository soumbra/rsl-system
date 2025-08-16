package com.rslsystem.api.constants;

public final class ApiConstants {
    
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
    
    // Service Info
    public static final String SERVICE_NAME = "RSL System API";
    public static final String SERVICE_VERSION = "1.0.0";
    public static final String SERVICE_DESCRIPTION = "API para gerenciamento de revisões sistemáticas da literatura";
    
    // Private constructor to prevent instantiation
    private ApiConstants() {
        throw new UnsupportedOperationException("Esta é uma classe de constantes e não pode ser instanciada");
    }
}
