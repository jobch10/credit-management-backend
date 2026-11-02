package com.project.creditmanagementbackend.app.exception.module;

import com.project.creditmanagementbackend.app.exception.ApiError;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum RoleErrors implements ApiError {
    ROLE_NOT_FOUND("ROL_001", "Uno o más roles no existen", HttpStatus.NOT_FOUND),
    ROLE_ALREADY_NAME_EXISTS("ROL_002", "El nombre del role ya existe", HttpStatus.CONFLICT),
    ROLE_ALREADY_CODE_EXISTS("ROL_003", "El código del role ya existe", HttpStatus.CONFLICT);

    private final String code;
    private final String message;
    private final HttpStatus status;

    RoleErrors(String code, String message, HttpStatus status) {
        this.code = code;
        this.message = message;
        this.status = status;
    }
}