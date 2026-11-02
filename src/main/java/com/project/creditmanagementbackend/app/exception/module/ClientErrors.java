package com.project.creditmanagementbackend.app.exception.module;

import com.project.creditmanagementbackend.app.exception.ApiError;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ClientErrors implements ApiError {
    CLIENT_NOT_FOUND("CLI_001", "Cliente no encontrado", HttpStatus.NOT_FOUND),
    CLIENT_CODE_ALREADY_EXISTS("CLI_002", "El código de cliente ya existe", HttpStatus.CONFLICT),
    ENTITY_REQUIRED("CLI_003", "La entidad asociada es obligatoria", HttpStatus.BAD_REQUEST);

    private final String code;
    private final String message;
    private final HttpStatus status;

    ClientErrors(String code, String message, HttpStatus status) {
        this.code = code;
        this.message = message;
        this.status = status;
    }
}
