package com.project.creditmanagementbackend.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.project.creditmanagementbackend.domain.enumerado.EnumSubjectStatus;
import com.project.creditmanagementbackend.domain.enumerado.EnumSubjectType;
import jakarta.validation.constraints.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TbFinancialSubjectDto implements Serializable {

    private Long id;

    @NotNull(message = "El tipo de sujeto es obligatorio")
    private EnumSubjectType subjectType;

    @NotBlank(message = "La dirección es obligatoria")
    @Size(max = 255)
    private String address;

    @Email(message = "El formato del correo no es válido")
    @Size(max = 100)
    private String email;

    @Size(max = 15)
    @Pattern(regexp = "^[+0-9 ]*$")
    private String phoneNumber;

    @NotNull(message = "El estado es obligatorio")
    private EnumSubjectStatus status;

    private TbPersonDto person;

    private List<TbSubjectRoleDto> roles;

    private TbCreditProfileDto creditProfile;

    private TbInvestorProfileDto investorProfile;
}