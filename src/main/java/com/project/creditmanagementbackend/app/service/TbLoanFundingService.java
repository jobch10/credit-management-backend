package com.project.creditmanagementbackend.app.service;

import com.project.creditmanagementbackend.app.util.service.GenericService;
import com.project.creditmanagementbackend.domain.dto.TbLoanFundingDto;

import java.util.List;

public interface TbLoanFundingService extends GenericService<TbLoanFundingDto, Long> {

    /** Registra un fondeo para el crédito. */
    TbLoanFundingDto addFunding(Long loanId, TbLoanFundingDto dto);

    /** Lista los fondeos de un crédito. */
    List<TbLoanFundingDto> findByLoan(Long loanId);
}