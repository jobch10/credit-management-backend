package com.project.creditmanagementbackend.app.exception.module;

import com.project.creditmanagementbackend.app.exception.ApiError;
import com.project.creditmanagementbackend.app.util.enumerado.TypeException;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static com.project.creditmanagementbackend.app.util.enumerado.TypeException.E;

@Getter
public enum PersonErrors implements ApiError {
    PERSON_NOT_FOUND("PER_001", "Prestatario no encontrado", "El prestatario solicitado no existe en el sistema", HttpStatus.NOT_FOUND, E),
    DNI_ALREADY_EXISTS("PER_002", "DNI ya registrado", "El número de DNI ingresado ya le pertenece a otro prestatario", HttpStatus.CONFLICT, E),
    EMAIL_ALREADY_EXISTS("PER_003", "Correo ya registrado", "El correo electrónico ya se encuentra en uso", HttpStatus.CONFLICT, E),
    UNDERAGE_NOT_ALLOWED("PER_004", "Menor de edad", "El prestatario debe ser mayor de edad para solicitar un crédito", HttpStatus.BAD_REQUEST, E),
    DNI_INCORRECT_FORMAT("PER_005", "Formato de DNI incorrecto", "El número de DNI ingresado no cumple con el formato válido", HttpStatus.BAD_REQUEST, E);

    private final String code;
    private final String title;
    private final String message;
    private final HttpStatus status;
    private final TypeException type;

    PersonErrors(String code, String title, String message, HttpStatus status, TypeException type) {
        this.code = code;
        this.title = title;
        this.message = message;
        this.status = status;
        this.type = type;
    }
}