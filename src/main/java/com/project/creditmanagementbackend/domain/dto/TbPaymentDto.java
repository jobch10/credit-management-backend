package com.project.creditmanagementbackend.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.project.creditmanagementbackend.domain.enumerado.EnumPaymentMethod;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TbPaymentDto implements Serializable {

    private Long id;

    @NotNull
    private Long loanId;

    private Long installmentId;

    @NotNull
    private LocalDate paymentDate;

    @NotNull @Positive
    private BigDecimal amount;

    @NotNull
    private EnumPaymentMethod paymentMethod;

    @Size(max = 50)
    private String reference;

    private String notes;
}