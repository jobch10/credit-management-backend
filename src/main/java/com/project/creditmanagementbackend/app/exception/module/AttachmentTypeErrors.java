package com.project.creditmanagementbackend.app.exception.module;

import com.project.creditmanagementbackend.app.exception.ApiError;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum AttachmentTypeErrors implements ApiError {
    ATTACHMENT_TYPE_CODE_ALREADY_EXISTS("AT_001", "Código Tipo Documento Ya Existe", HttpStatus.CONFLICT),
    ATTACHMENT_TYPE_NOT_FOUND("AT_002", "Tipo Documento no encontrado", HttpStatus.NOT_FOUND),
    ATTACHMENT_TYPE_IN_USE("AT_003", "Tipo Documento en uso, no se puede eliminar", HttpStatus.BAD_REQUEST);
    private final String code;
    private final String message;
    private final HttpStatus status;

    AttachmentTypeErrors(String code, String message, HttpStatus status) {
        this.code = code;
        this.message = message;
        this.status = status;
    }
}
