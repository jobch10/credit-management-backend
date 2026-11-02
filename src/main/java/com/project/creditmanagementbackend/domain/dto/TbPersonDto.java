package com.project.creditmanagementbackend.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TbPersonDto implements Serializable {

    private Long id;

    @NotNull(message = "El sujeto financiero es obligatorio")
    private Long financialSubjectId;

    @NotBlank(message = "El DNI es obligatorio")
    @Size(min = 8, max = 8)
    @Pattern(regexp = "^[0-9]+$")
    private String dni;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100)
    private String firstName;

    @NotBlank(message = "El apellido es obligatorio")
    @Size(max = 100)
    private String lastName;

    @NotNull(message = "La fecha de nacimiento es obligatoria")
    @Past
    private LocalDate birthDate;

    @NotBlank(message = "El estado civil es obligatorio")
    @Size(max = 20)
    private String maritalStatus;
}