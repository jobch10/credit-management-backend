package com.project.creditmanagementbackend.domain.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@io.swagger.v3.oas.annotations.media.Schema(description = "Respuesta de autenticación exitosa")
public class LoginResponse {
    @NotBlank(message = "El token es obligatorio")
    @Schema(description = "Token JWT para autorización", example = "eyJhbGciOiJIUzI1NiJ9...")
    private String token;

    @NotBlank(message = "El refresh token es obligatorio")
    private String refreshToken;

    @Builder.Default
    @NotBlank(message = "El tipo de token es obligatorio")
    @Size(max = 100, message = "El tipo no puede superar los 100 caracteres")
    @io.swagger.v3.oas.annotations.media.Schema(description = "Tipo de token", example = "Bearer")
    private String type = "Bearer";

    @Positive(message = "El tiempo de expiración debe ser positivo")
    @io.swagger.v3.oas.annotations.media.Schema(description = "Tiempo de expiración en segundos", example = "3600")
    private long expiresIn;

    @Positive(message = "El tiempo de expiración debe ser positivo")
    @io.swagger.v3.oas.annotations.media.Schema(description = "Tiempo de expiración en segundos", example = "64600")
    private long refreshExpiresIn;

}
