package com.project.creditmanagementbackend.domain.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Datos requeridos para registrar un nuevo usuario")
public class RegisterRequestDto {

    @NotBlank(message = "Los nombres son obligatorios")
    @Size(max = 100, message = "Los nombres no pueden superar los 100 caracteres")
    @Schema(description = "Nombre completo del usuario", example = "Juan Pérez")
    private String names;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El email debe ser válido")
    @Size(max = 100, message = "El email no puede superar los 100 caracteres")
    @Schema(description = "Correo electrónico institucional", example = "juan.perez@sistema.com")
    private String email;

    @NotBlank(message = "El usuario de login es obligatorio")
    @Size(max = 100, message = "El usuario no puede superar los 100 caracteres")
    @Schema(description = "Nombre de usuario para el inicio de sesión", example = "jperez")
    private String username;

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(max = 100, message = "La contraseña no puede superar los 100 caracteres")
    @Schema(description = "Contraseña de acceso (será hasheada)", example = "SecurePass123$")
    private String password;

    @Schema(description = "Id del role a asignar", example = "1")
    private Integer roleId;
}
