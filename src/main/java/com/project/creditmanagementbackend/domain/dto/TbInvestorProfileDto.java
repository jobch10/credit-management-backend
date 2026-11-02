package com.project.creditmanagementbackend.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.project.creditmanagementbackend.domain.enumerado.EnumRiskTolerance;
import jakarta.validation.constraints.*;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TbInvestorProfileDto implements Serializable {

    private Long id;

    @NotNull(message = "El sujeto financiero es obligatorio")
    private Long financialSubjectId;

    @NotNull(message = "La capacidad de inversión es obligatoria")
    @PositiveOrZero(message = "La capacidad de inversión debe ser mayor o igual a cero")
    private BigDecimal investmentCapacity;

    @PositiveOrZero
    @DecimalMax(value = "100.00", message = "La tasa no puede superar 100%")
    private BigDecimal preferredRate;

    @Min(value = 1, message = "El plazo mínimo es 1 mes")
    private Integer preferredTermMonths;

    private EnumRiskTolerance riskTolerance;

    @Size(max = 100)
    private String bankName;

    @Size(max = 30)
    private String bankAccount;

    @Size(max = 1000)
    private String notes;

    // Solo lectura
    private BigDecimal totalInvested;
    private Integer activeInvestments;
}