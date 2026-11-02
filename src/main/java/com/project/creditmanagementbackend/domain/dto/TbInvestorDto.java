package com.project.creditmanagementbackend.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TbInvestorDto implements Serializable {
    private Integer id;
    private Integer entityId;
    private TbEntityDto entity;
    private String investorCode;
    private BigDecimal investmentCapacity;
    private BigDecimal preferredRate;
    private String notes;
    private boolean status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
