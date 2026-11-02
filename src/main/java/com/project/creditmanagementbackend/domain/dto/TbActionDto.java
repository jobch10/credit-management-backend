package com.project.creditmanagementbackend.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import jakarta.validation.constraints.*;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TbActionDto implements Serializable {
    private Integer id;

    @NotBlank(message = "El código es obligatorio")
    @Size(max = 20, message = "El código no puede superar los 20 caracteres")
    private String code;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "El nombre no puede superar los 100 caracteres")
    private String name;

    private String icon;

    private String color;

    private boolean state;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
