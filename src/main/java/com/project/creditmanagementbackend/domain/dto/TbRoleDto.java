package com.project.creditmanagementbackend.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import jakarta.validation.constraints.*;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Representación de un role de usuario")
public class TbRoleDto implements Serializable {

    private Integer id;

    @NotBlank(message = "El código es obligatorio")
    @Size(max = 20, message = "El código no puede superar los 20 caracteres")
    @Schema(description = "Código único del role", example = "ADMIN")
    private String code;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "El nombre no puede superar los 100 caracteres")
    @Schema(description = "Nombre del role", example = "Administrador")
    private String name;

    private boolean state;

    @Schema(description = "Fecha de creación")
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @Schema(description = "Lista de permisos asociados")
    private List<TbModuleGroupDto> modules;
}
