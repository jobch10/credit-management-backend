package com.project.creditmanagementbackend.domain.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;
import jakarta.validation.constraints.*;

@Data
public class PermisoRequestDto {
    @Positive(message = "El ID del módulo debe ser positivo")
    @Schema(description = "ID del Módulo")
    private Integer moduloId;

    @NotEmpty(message = "Debe enviar al menos una acción")
    @Schema(description = "Lista de IDs de Actions")
    private List<Integer> accionId;
}
