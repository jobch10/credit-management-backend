package com.project.creditmanagementbackend.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.project.creditmanagementbackend.domain.enumerado.EnumEmploymentType;
import com.project.creditmanagementbackend.domain.enumerado.EnumInfocorpStatus;
import jakarta.validation.constraints.*;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TbCreditProfileDto implements Serializable {

    private Long id;

    @NotNull(message = "El sujeto financiero es obligatorio")
    private Long financialSubjectId;

    @NotNull
    @Min(0)
    private Integer dependentsCount;

    @NotBlank
    @Size(max = 20)
    private String housingType;

    @NotNull
    @PositiveOrZero
    private BigDecimal monthlyIncome;

    @NotNull
    @PositiveOrZero
    private BigDecimal fixedExpenses;

    @NotNull
    @PositiveOrZero
    private BigDecimal externalDebtPayments;

    // Situación laboral
    @Size(max = 100)
    private String occupation;

    @Size(max = 150)
    private String employer;

    @Min(0)
    private Integer workSeniorityMonths;

    private EnumEmploymentType employmentType;

    // Verificación externa
    private EnumInfocorpStatus infocorpStatus;

    @Min(0) @Max(999)
    private Integer infocorpScore;

    private Boolean hasPoliceRecords;
    private Boolean hasJudicialRecords;
    private Boolean hasCriminalRecords;

    // Control interno
    private Integer totalLoansApproved;
    private Integer maxDaysInArrears;
    private Integer timesRefinanced;
    private Integer creditScore;
    private String riskLevel;
}