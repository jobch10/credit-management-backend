package com.project.creditmanagementbackend.app.service;

import com.project.creditmanagementbackend.app.util.service.GenericService;
import com.project.creditmanagementbackend.domain.dto.TbPaymentDto;

import java.util.List;

public interface TbPaymentService extends GenericService<TbPaymentDto, Long> {

    /** Registra un pago y actualiza la cuota correspondiente. */
    TbPaymentDto registerPayment(TbPaymentDto dto);

    /** Lista pagos de un crédito. */
    List<TbPaymentDto> findByLoan(Long loanId);
}