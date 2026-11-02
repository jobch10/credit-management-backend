package com.project.creditmanagementbackend.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.project.creditmanagementbackend.domain.enumerado.EnumLoanFundingStatus;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Builder(toBuilder = true) @NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TbLoanFundingDto implements Serializable {

    private Long id;

    @NotNull
    private Long loanId;

    @NotNull
    private Long investorSubjectId;

    @NotNull @PositiveOrZero
    private BigDecimal fundedAmount;

    @NotNull @PositiveOrZero @DecimalMax("100.00")
    private BigDecimal fundedPercentage;

    @PositiveOrZero @DecimalMax("100.00")
    private BigDecimal expectedRate;

    private EnumLoanFundingStatus status;
}