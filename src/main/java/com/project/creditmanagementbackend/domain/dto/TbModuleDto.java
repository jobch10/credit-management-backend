package com.project.creditmanagementbackend.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.project.creditmanagementbackend.domain.enumerado.ModuleType;
import jakarta.validation.constraints.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TbModuleDto implements Serializable {

    private Integer id;

    @NotBlank(message = "El código del módulo es obligatorio")
    @Size(max = 20)
    private String code;

    @NotBlank(message = "El nombre del módulo es obligatorio")
    @Size(max = 100)
    private String name;

    @Size(max = 255)
    private String description;

    @Size(max = 100)
    private String icon;

    @Size(max = 255)
    private String route;

    @NotNull(message = "El tipo de módulo es obligatorio")
    private ModuleType type;

    @Min(value = 0)
    private Integer orderNum;

    private Integer parentId;

    private boolean state;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @Builder.Default
    private List<TbActionDto> actions = new ArrayList<>();

    @Builder.Default
    private List<TbModuleDto> children = new ArrayList<>();

}