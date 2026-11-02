package com.project.creditmanagementbackend.app.service.impl;

import com.project.creditmanagementbackend.app.dao.TbCreditProfileDao;
import com.project.creditmanagementbackend.app.dao.TbInstallmentDao;
import com.project.creditmanagementbackend.app.dao.TbLoanDao;
import com.project.creditmanagementbackend.app.dao.TbPaymentDao;
import com.project.creditmanagementbackend.app.exception.BusinessException;
import com.project.creditmanagementbackend.app.exception.module.GeneralErrors;
import com.project.creditmanagementbackend.app.service.TbPaymentService;
import com.project.creditmanagementbackend.app.util.service.impl.GenericServiceImpl;
import com.project.creditmanagementbackend.domain.dto.TbPaymentDto;
import com.project.creditmanagementbackend.domain.entity.TbInstallment;
import com.project.creditmanagementbackend.domain.entity.TbLoan;
import com.project.creditmanagementbackend.domain.entity.TbPayment;
import com.project.creditmanagementbackend.domain.enumerado.EnumInstallmentStatus;
import com.project.creditmanagementbackend.domain.enumerado.EnumLoanStatus;
import com.project.creditmanagementbackend.domain.mapper.TbPaymentMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
public class TbPaymentServiceImpl
        extends GenericServiceImpl<TbPaymentDto, TbPayment, Long, TbPaymentMapper>
        implements TbPaymentService {

    private final TbPaymentDao paymentDao;
    private final TbLoanDao loanDao;
    private final TbInstallmentDao installmentDao;
    private final TbCreditProfileDao creditProfileDao;

    public TbPaymentServiceImpl(
            TbPaymentDao paymentDao,
            TbLoanDao loanDao,
            TbInstallmentDao installmentDao,
            TbCreditProfileDao creditProfileDao,
            TbPaymentMapper paymentMapper) {
        super(paymentDao, paymentMapper);
        this.paymentDao = paymentDao;
        this.loanDao = loanDao;
        this.installmentDao = installmentDao;
        this.creditProfileDao = creditProfileDao;
    }

    @Override
    public TbPaymentDto registerPayment(TbPaymentDto dto) {
        TbLoan loan = loanDao.findById(dto.getLoanId())
                .orElseThrow(() -> new BusinessException(GeneralErrors.ENTITY_NOT_FOUND));

        TbInstallment installment = null;
        if (dto.getInstallmentId() != null) {
            installment = installmentDao.findById(dto.getInstallmentId())
                    .orElseThrow(() -> new BusinessException(GeneralErrors.ENTITY_NOT_FOUND));
        }

        TbPayment payment = mapper.toEntity(dto);
        payment.setLoan(loan);
        payment.setInstallment(installment);
        payment = paymentDao.save(payment);

        if (installment != null) {
            applyPaymentToInstallment(installment, payment);
        }

        updateLoanStatus(loan);
        updateMaxDaysInArrears(loan);

        return mapper.toDto(payment);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TbPaymentDto> findByLoan(Long loanId) {
        return paymentDao.findByLoanIdOrderByPaymentDateDesc(loanId).stream()
                .map(mapper::toBasicDto)
                .toList();
    }

    private void applyPaymentToInstallment(TbInstallment installment, TbPayment payment) {
        BigDecimal newPaid = installment.getPaidAmount().add(payment.getAmount());
        installment.setPaidAmount(newPaid);

        if (newPaid.compareTo(installment.getTotalAmount()) >= 0) {
            installment.setStatus(EnumInstallmentStatus.PAGADA);
            installment.setPaidDate(payment.getPaymentDate());
        } else if (newPaid.compareTo(BigDecimal.ZERO) > 0) {
            installment.setStatus(EnumInstallmentStatus.PARCIAL);
        }

        installmentDao.save(installment);
    }

    private void updateLoanStatus(TbLoan loan) {
        List<TbInstallment> installments = installmentDao.findByLoanIdOrderByInstallmentNumberAsc(loan.getId());
        boolean allPaid = installments.stream()
                .allMatch(i -> i.getStatus() == EnumInstallmentStatus.PAGADA);
        if (allPaid) {
            loan.setStatus(EnumLoanStatus.PAGADO);
            loanDao.save(loan);
        }
    }

    private void updateMaxDaysInArrears(TbLoan loan) {
        creditProfileDao.findByFinancialSubjectId(loan.getBorrowerSubject().getId())
                .ifPresent(profile -> {
                    int maxDays = installmentDao.findByLoanIdOrderByInstallmentNumberAsc(loan.getId())
                            .stream()
                            .mapToInt(i -> i.getDaysLate() != null ? i.getDaysLate() : 0)
                            .max().orElse(0);
                    if (maxDays > (profile.getMaxDaysInArrears() != null ? profile.getMaxDaysInArrears() : 0)) {
                        profile.setMaxDaysInArrears(maxDays);
                        creditProfileDao.save(profile);
                    }
                });
    }
}