package com.project.creditmanagementbackend.app.exception.module;

import com.project.creditmanagementbackend.app.exception.ApiError;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum EntityErrors implements ApiError {
    ENTITY_NOT_FOUND("ENT_001", "Entidad no encontrada", HttpStatus.NOT_FOUND),
    DOCUMENT_NUMBER_ALREADY_EXISTS("ENT_002", "El número de documento ya está registrado", HttpStatus.CONFLICT),
    TIPO_DOCUMENTO_NOT_FOUND("ENT_003", "Tipo de documento no encontrado", HttpStatus.NOT_FOUND),
    DOCUMENT_NUMBER_REQUIRED("ENT_004", "El número de documento es requerido", HttpStatus.BAD_REQUEST),
    TIPO_DOCUMENTO_REQUIRED("ENT_005", "El tipo de documento es requerido para crear una entidad", HttpStatus.BAD_REQUEST);

    private final String code;
    private final String message;
    private final HttpStatus status;

    EntityErrors(String code, String message, HttpStatus status) {
        this.code = code;
        this.message = message;
        this.status = status;
    }
}
