package com.project.creditmanagementbackend.app.exception.module;

import com.project.creditmanagementbackend.app.exception.ApiError;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum InvestorErrors implements ApiError {
    INVESTOR_NOT_FOUND("INV_001", "Inversionista no encontrado", HttpStatus.NOT_FOUND),
    INVESTOR_CODE_ALREADY_EXISTS("INV_002", "El código de inversionista ya existe", HttpStatus.CONFLICT);

    private final String code;
    private final String message;
    private final HttpStatus status;

    InvestorErrors(String code, String message, HttpStatus status) {
        this.code = code;
        this.message = message;
        this.status = status;
    }
}
