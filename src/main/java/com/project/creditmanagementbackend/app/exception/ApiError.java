package com.project.creditmanagementbackend.app.exception;

import com.project.creditmanagementbackend.app.util.enumerado.TypeException;
import org.springframework.http.HttpStatus;

public interface ApiError {
    String getCode();
    String getMessage();
    HttpStatus getStatus();

    default String getTitle() {
        return "";
    }

    default TypeException getType() {return TypeException.E;}
}