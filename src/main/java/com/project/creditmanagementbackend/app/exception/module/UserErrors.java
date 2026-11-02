package com.project.creditmanagementbackend.app.exception.module;
import com.project.creditmanagementbackend.app.exception.ApiError;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum UserErrors implements ApiError {
    USER_ALREADY_EXISTS("USR_001", "El nombre de usuario ya existe", HttpStatus.CONFLICT),
    EMAIL_ALREADY_EXISTS("USR_002", "El email ya existe", HttpStatus.CONFLICT),
    USER_NOT_FOUND("USR_404", "Usuario no encontrado", HttpStatus.NOT_FOUND);

    private final String code;
    private final String message;
    private final HttpStatus status;

    UserErrors(String code, String message, HttpStatus status) {
        this.code = code;
        this.message = message;
        this.status = status;
    }
}