package com.project.creditmanagementbackend.app.exception.module;

import com.project.creditmanagementbackend.app.exception.ApiError;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ActionErrors implements ApiError {
    ACCION_NOT_FOUND("ACT_001", "", "Acción no encontrada", HttpStatus.NOT_FOUND),
    ACCION_CODE_ALREADY_EXISTS("ACT_002", "", "El código de la acción ya existe", HttpStatus.CONFLICT),
    ACCION_NAME_ALREADY_EXISTS("ACT_003", "", "El nombre de la acción ya existe", HttpStatus.CONFLICT),
    ACCION_HAS_DEPENDENCIES("ACT_004", "Dependencias", "La acción tiene registros dependientes y no puede ser eliminada", HttpStatus.CONFLICT);

    private final String code;
    private final String title;
    private final String message;
    private final HttpStatus status;

    ActionErrors(String code, String title, String message, HttpStatus status) {
        this.code = code;
        this.title = title;
        this.message = message;
        this.status = status;
    }
}