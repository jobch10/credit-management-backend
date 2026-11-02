package com.project.creditmanagementbackend.app.util.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@io.swagger.v3.oas.annotations.media.Schema(description = "Estructura estándar de respuesta de la API")
public class ApiResponse<T> {

    private LocalDateTime timestamp;
    private int status;
    private boolean success;

    @Builder.Default
    private String title = "";

    private String message;
    private T data;
    private String path;
    private List<String> errors;
    private String type;

    public static <T> ApiResponse<T> success(T data, String title, String message, String path) {
        return ApiResponse.<T>builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.OK.value())
                .success(true)
                .title(title != null ? title : "")
                .message(message)
                .data(data)
                .path(path)
                .build();
    }

    public static <T> ApiResponse<T> success(T data, String message, String path) {
        return success(data, "", message, path);
    }

    public static <T> ApiResponse<T> created(T data, String title, String message, String path) {
        return ApiResponse.<T>builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.CREATED.value())
                .success(true)
                .title(title != null ? title : "")
                .message(message)
                .data(data)
                .path(path)
                .build();
    }

    public static <T> ApiResponse<T> created(T data, String message, String path) {
        return created(data, "", message, path);
    }

    public static <T> ApiResponse<T> error(HttpStatus status, String title, String message, String path, List<String> errors) {
        return ApiResponse.<T>builder()
                .timestamp(LocalDateTime.now())
                .status(status.value())
                .success(false)
                .title(title != null ? title : "")
                .message(message)
                .path(path)
                .errors(errors)
                .build();
    }

    public static <T> ApiResponse<T> error(HttpStatus status, String message, String path, List<String> errors) {
        return error(status, "", message, path, errors);
    }

    public static <T> ApiResponse<T> badRequest(String message, String path) {
        return error(HttpStatus.BAD_REQUEST, "Solicitud incorrecta", message, path, null);
    }

    public static <T> ApiResponse<T> badRequest(String message, String path, List<String> errors) {
        return error(HttpStatus.BAD_REQUEST, "Solicitud incorrecta", message, path, errors);
    }

    public static <T> ApiResponse<T> unauthorized(String message, String path) {
        return error(HttpStatus.UNAUTHORIZED, "No autorizado", message, path, null);
    }

    public static <T> ApiResponse<T> forbidden(String message, String path) {
        return error(HttpStatus.FORBIDDEN, "Acceso denegado", message, path, null);
    }

    public static <T> ApiResponse<T> notFound(String message, String path) {
        return error(HttpStatus.NOT_FOUND, "Recurso no encontrado", message, path, null);
    }

    public static <T> ApiResponse<T> conflict(String message, String path) {
        return error(HttpStatus.CONFLICT, "Conflicto", message, path, null);
    }

    public static <T> ApiResponse<T> unprocessable(String message, String path, List<String> errors) {
        return error(HttpStatus.UNPROCESSABLE_ENTITY, "Entidad no procesable", message, path, errors);
    }

    public static <T> ApiResponse<T> tooManyRequests(String message, String path) {
        return error(HttpStatus.TOO_MANY_REQUESTS, "Demasiadas solicitudes", message, path, null);
    }

    public static <T> ApiResponse<T> serverError(String message, String path) {
        return error(HttpStatus.INTERNAL_SERVER_ERROR, "Error interno del servidor", message, path, null);
    }

    public static <T> ApiResponse<T> serviceUnavailable(String message, String path) {
        return error(HttpStatus.SERVICE_UNAVAILABLE, "Servicio no disponible", message, path, null);
    }
}