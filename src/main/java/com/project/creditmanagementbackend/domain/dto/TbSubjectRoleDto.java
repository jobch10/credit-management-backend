package com.project.creditmanagementbackend.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.project.creditmanagementbackend.domain.enumerado.EnumSubjectRole;
import jakarta.validation.constraints.*;
import lombok.*;

import java.io.Serializable;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TbSubjectRoleDto implements Serializable {

    private Long id;

    @NotNull(message = "El sujeto financiero es obligatorio")
    private Long financialSubjectId;

    @NotNull(message = "El rol es obligatorio")
    private EnumSubjectRole role;
}