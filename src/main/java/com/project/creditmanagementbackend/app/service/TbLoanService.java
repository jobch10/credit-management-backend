package com.project.creditmanagementbackend.app.service;

import com.project.creditmanagementbackend.app.util.service.GenericService;
import com.project.creditmanagementbackend.domain.dto.TbLoanDto;

import java.util.List;

public interface TbLoanService extends GenericService<TbLoanDto, Long> {

    /** Crea el crédito y genera el cronograma de cuotas. */
    TbLoanDto createLoan(TbLoanDto dto);

    /** Obtiene el crédito con su cronograma completo. */
    TbLoanDto getFullLoan(Long id);

    /** Lista créditos de un prestatario. */
    List<TbLoanDto> findByBorrowerSubject(Long subjectId);

    /** Calcula cuota, interés y total sin guardar. */
    TbLoanDto calculate(TbLoanDto dto);
}
