package com.project.creditmanagementbackend.app.exception;

import com.project.creditmanagementbackend.app.util.enumerado.TypeException;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApiException extends RuntimeException {

    private final String code;
    private final String title;
    private final HttpStatus status;
    private final TypeException type;

    public ApiException(ApiError error) {
        super(error.getMessage());
        this.code = error.getCode();
        this.status = error.getStatus();
        this.title = error.getTitle();
        this.type = error.getType();
    }
}