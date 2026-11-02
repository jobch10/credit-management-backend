package com.project.creditmanagementbackend.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.project.creditmanagementbackend.domain.enumerado.EnumMovimientoAuditoria;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import jakarta.validation.constraints.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@io.swagger.v3.oas.annotations.media.Schema(description = "Registro de auditoría de actividad")
public class TbAuditoriaDto implements Serializable {

    @io.swagger.v3.oas.annotations.media.Schema(description = "ID del log", example = "105")
    private Integer id;

    @io.swagger.v3.oas.annotations.media.Schema(description = "Usuario que realizó la acción")
    private TbUsuarioDto usuario;

    @io.swagger.v3.oas.annotations.media.Schema(description = "Tipo de operación", example = "INSERT")
    private EnumMovimientoAuditoria tipo;

    @NotBlank(message = "La entidad es obligatoria")
    @Size(max = 100, message = "La entidad no puede superar los 100 caracteres")
    @io.swagger.v3.oas.annotations.media.Schema(description = "Nombre de la entidad afectada", example = "TB_USUARIO")
    private String entidad;

    @Positive(message = "El ID de entidad debe ser positivo")
    @io.swagger.v3.oas.annotations.media.Schema(description = "ID de la entidad afectada", example = "1")
    private Integer entidadId;

    @io.swagger.v3.oas.annotations.media.Schema(description = "Estado previo de la entidad (JSON)")
    private String antesJson;

    @io.swagger.v3.oas.annotations.media.Schema(description = "Estado posterior de la entidad (JSON)")
    private String despuesJson;

    @NotBlank(message = "La IP es obligatoria")
    @Size(max = 100, message = "La IP no puede superar los 100 caracteres")
    @io.swagger.v3.oas.annotations.media.Schema(description = "Dirección IP del cliente", example = "192.168.1.5")
    private String ip;

    @io.swagger.v3.oas.annotations.media.Schema(description = "Fecha de la operación")
    private LocalDateTime createdAt;
}
