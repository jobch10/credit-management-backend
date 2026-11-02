package com.project.creditmanagementbackend.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@io.swagger.v3.oas.annotations.media.Schema(description = "Relación entre un usuario y un role")
public class TbUsuarioRolDto implements Serializable {

    @io.swagger.v3.oas.annotations.media.Schema(description = "ID de la relación", example = "1")
    private Integer id;

    @com.fasterxml.jackson.annotation.JsonIgnoreProperties("roles")
    private TbUsuarioDto usuario;
    private TbRoleDto role;

    @io.swagger.v3.oas.annotations.media.Schema(description = "Fecha de asignación")
    private LocalDateTime creadoEn;

    private LocalDateTime actualizadoEn;

}
