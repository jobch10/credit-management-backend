package com.project.creditmanagementbackend.app.exception.module;
import com.project.creditmanagementbackend.app.exception.ApiError;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum PermissionErrors implements ApiError {
    PERMISSION_NOT_FOUND("PER_001", "Uno o más permisos no existen", HttpStatus.NOT_FOUND),
    PERMISSION_ALREADY_EXISTS("PER_002", "Este permiso ya existe", HttpStatus.CONFLICT);

    private final String code;
    private final String message;
    private final HttpStatus status;

    PermissionErrors(String code, String message, HttpStatus status) {
        this.code = code;
        this.message = message;
        this.status = status;
    }
}