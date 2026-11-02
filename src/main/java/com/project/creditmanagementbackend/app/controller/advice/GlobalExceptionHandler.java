package com.project.creditmanagementbackend.app.controller.advice;

import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.project.creditmanagementbackend.app.exception.BusinessException;
import com.project.creditmanagementbackend.app.util.enumerado.TypeException;
import com.project.creditmanagementbackend.app.util.response.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiResponse<Object>> handleBusiness(
            BusinessException ex,
            HttpServletRequest request) {

        return buildResponse(
                ex.getStatus(),
                ex.getTitle() != null ? ex.getTitle() : "Error",
                ex.getMessage(),
                ex.getType(),
                request,
                null
        );
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<Object> handleMethodNotSupported(HttpRequestMethodNotSupportedException ex) {
        return ResponseEntity
                .status(HttpStatus.METHOD_NOT_ALLOWED)
                .body(Map.of(
                        "status", HttpStatus.METHOD_NOT_ALLOWED.value(),
                        "title", "Método no permitido",
                        "message", "El método HTTP '" + ex.getMethod() + "' no está soportado en esta ruta",
                        "type", TypeException.E.name()
                ));
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiResponse<Object>> handleBadCredentials(
            BadCredentialsException ex,
            HttpServletRequest request) {

        return buildResponse(
                HttpStatus.UNAUTHORIZED,
                "Autenticación fallida",
                "Credenciales incorrectas",
                TypeException.E,
                request,
                null
        );
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handleUserNotFound(
            UsernameNotFoundException ex,
            HttpServletRequest request) {

        return buildResponse(
                HttpStatus.NOT_FOUND,
                "Usuario no encontrado",
                ex.getMessage(),
                TypeException.E,
                request,
                null
        );
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiResponse<Object>> handleAccessDenied(
            AccessDeniedException ex,
            HttpServletRequest request) {

        return buildResponse(
                HttpStatus.FORBIDDEN,
                "Acceso denegado",
                "No tienes permisos para esta acción",
                TypeException.E,
                request,
                null
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Object>> handleValidation(
            MethodArgumentNotValidException ex,
            HttpServletRequest request) {

        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .toList();

        return buildResponse(
                HttpStatus.BAD_REQUEST,
                "Error de validación",
                "Datos enviados inválidos",
                TypeException.E,
                request,
                errors
        );
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse<Object>> handleJsonParse(
            HttpMessageNotReadableException ex,
            HttpServletRequest request) {

        String message = "JSON mal formado o tipo de dato incorrecto";

        if (ex.getCause() instanceof MismatchedInputException mie) {
            message = "JSON inválido: " + mie.getOriginalMessage();
        }

        log.error("[API ERROR] JSON parse error: {}", ex.getMessage(), ex);

        return buildResponse(
                HttpStatus.BAD_REQUEST,
                "Error de formato JSON",
                message,
                TypeException.E,
                request,
                null
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleGeneral(
            Exception ex,
            HttpServletRequest request) {

        log.error("[API ERROR] status=500, message={}", ex.getMessage(), ex);

        return buildResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Error interno",
                "Error interno del servidor",
                TypeException.E,
                request,
                null
        );
    }

    private ResponseEntity<ApiResponse<Object>> buildResponse(
            HttpStatus status,
            String title,
            String message,
            TypeException type,
            HttpServletRequest request,
            List<String> errors) {

        log.error("[API ERROR] status={}, title={}, message={}, type={}",
                status.value(), title, message, type);

        ApiResponse<Object> response = ApiResponse.builder()
                .timestamp(java.time.LocalDateTime.now())
                .status(status.value())
                .success(false)
                .title(title != null ? title : "")
                .message(message)
                .type(type != null ? type.name() : TypeException.E.name())
                .path(request.getRequestURI())
                .errors(errors)
                .build();

        return new ResponseEntity<>(response, status);
    }
}