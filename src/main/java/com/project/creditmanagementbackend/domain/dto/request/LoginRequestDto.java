package com.project.creditmanagementbackend.domain.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Solicitud para inicio de sesión")
public class LoginRequestDto {

    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "Debe ser un correo electrónico válido")
    @Size(max = 100, message = "El correo no puede superar los 100 caracteres")
    @Schema(description = "Correo electrónico para acceder", example = "admin@example.com")
    private String email;

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(max = 100, message = "La contraseña no puede superar los 100 caracteres")
    @Schema(description = "Contraseña en texto plano", example = "Admin123*")
    private String password;
}
