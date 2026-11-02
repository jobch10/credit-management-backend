package com.project.creditmanagementbackend.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.project.creditmanagementbackend.domain.enumerado.EnumNivelRiesgo;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TbClientDto implements Serializable {
    private Integer id;
    private Integer entityId;
    private TbEntityDto entity;
    private String clientCode;
    private EnumNivelRiesgo riskLevel;
    private Integer creditScore;
    private BigDecimal monthlyIncome;
    private String economicActivity;
    private BigDecimal creditLimit;
    private BigDecimal availableCredit;
    private LocalDate lastEvaluationDate;
    private String notes;
    private boolean state;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
