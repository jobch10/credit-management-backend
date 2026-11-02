package com.project.creditmanagementbackend.app.exception.module;
import com.project.creditmanagementbackend.app.exception.ApiError;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum GeneralErrors implements ApiError {
    // 400 - Errores del cliente
    DATA_INTEGRITY_ERROR    ("GEN_001", "Error de integridad de datos", HttpStatus.BAD_REQUEST),
    DATA_NOT_COMPLETED      ("GEN_002", "No se obtuvo la data necesaria para realizar el registro", HttpStatus.BAD_REQUEST),
    INVALID_FORMAT          ("GEN_003", "El formato de los datos es inválido", HttpStatus.BAD_REQUEST),

    // 401 - Autenticación
    TOKEN_EXPIRED           ("GEN_401", "El token ha expirado", HttpStatus.UNAUTHORIZED),

    // 404 - No encontrado
    ENTITY_NOT_FOUND        ("GEN_404", "Entidad no encontrada", HttpStatus.NOT_FOUND),

    // 500 - Errores del servidor
    REPOSITORY_NOT_SPECIFICATION("GEN_500", "El repositorio no soporta especificaciones", HttpStatus.INTERNAL_SERVER_ERROR),
    INTERNAL_ERROR          ("GEN_501", "Error interno del servidor", HttpStatus.INTERNAL_SERVER_ERROR),

    // 502/504 - Errores de servicios externos
    EXTERNAL_SERVICE_ERROR  ("GEN_502", "Error al comunicarse con un servicio externo", HttpStatus.BAD_GATEWAY),
    EXTERNAL_SERVICE_TIMEOUT("GEN_504", "El servicio externo no respondió a tiempo", HttpStatus.GATEWAY_TIMEOUT);

    private final String code;
    private final String message;
    private final HttpStatus status;

    GeneralErrors(String code, String message, HttpStatus status) {
        this.code = code;
        this.message = message;
        this.status = status;
    }
}