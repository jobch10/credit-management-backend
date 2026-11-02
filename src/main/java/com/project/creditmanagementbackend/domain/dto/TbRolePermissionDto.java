package com.project.creditmanagementbackend.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Representación de un permiso asociado a un role y módulo")
public class TbRolePermissionDto implements Serializable {

    private Integer id;

    @Schema(description = "Módulo asociado")
    private TbModuleDto module;

    @Schema(description = "Acción asociada")
    private TbActionDto action;

    private Boolean state;
    private LocalDateTime updatedAt;
    private LocalDateTime createdAt;
}
