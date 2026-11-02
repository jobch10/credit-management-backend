package com.project.creditmanagementbackend.app.service;

import com.project.creditmanagementbackend.app.util.service.GenericService;
import com.project.creditmanagementbackend.domain.dto.TbInstallmentDto;

import java.util.List;

public interface TbInstallmentService extends GenericService<TbInstallmentDto, Long> {

    List<TbInstallmentDto> findByLoan(Long loanId);

    List<TbInstallmentDto> findPendingByLoan(Long loanId);
}