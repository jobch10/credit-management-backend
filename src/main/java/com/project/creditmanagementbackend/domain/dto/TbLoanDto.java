package com.project.creditmanagementbackend.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.project.creditmanagementbackend.domain.enumerado.EnumGuaranteeType;
import com.project.creditmanagementbackend.domain.enumerado.EnumLoanStatus;
import com.project.creditmanagementbackend.domain.enumerado.EnumPaymentFrequency;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TbLoanDto implements Serializable {

    private Long id;
    private Long borrowerSubjectId;

    @NotBlank
    @Size(max = 20)
    private String loanNumber;

    @NotNull
    @PositiveOrZero
    private BigDecimal principalAmount;

    @NotNull @PositiveOrZero @DecimalMax("100.00")
    private BigDecimal interestRate;

    @NotNull @Min(1)
    private Integer termMonths;

    @NotNull
    private EnumPaymentFrequency paymentFrequency;

    @NotNull
    private LocalDate startDate;

    private LocalDate endDate;
    private BigDecimal totalInterest;
    private BigDecimal totalToPay;
    private EnumLoanStatus status;
    private EnumGuaranteeType guaranteeType;
    private String guaranteeDescription;
    private String notes;

    // Solo lectura para calculo
    private BigDecimal monthlyPayment;

    private List<TbInstallmentDto> installments;
    private List<TbLoanFundingDto> fundings;
}