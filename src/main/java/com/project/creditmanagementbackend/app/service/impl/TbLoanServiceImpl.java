package com.project.creditmanagementbackend.app.service.impl;

import com.project.creditmanagementbackend.app.dao.TbFinancialSubjectDao;
import com.project.creditmanagementbackend.app.dao.TbInstallmentDao;
import com.project.creditmanagementbackend.app.dao.TbLoanDao;
import com.project.creditmanagementbackend.app.dao.TbLoanFundingDao;
import com.project.creditmanagementbackend.app.exception.BusinessException;
import com.project.creditmanagementbackend.app.exception.module.GeneralErrors;
import com.project.creditmanagementbackend.app.service.TbLoanService;
import com.project.creditmanagementbackend.app.util.service.impl.GenericServiceImpl;
import com.project.creditmanagementbackend.domain.dto.TbLoanDto;
import com.project.creditmanagementbackend.domain.dto.TbLoanFundingDto;
import com.project.creditmanagementbackend.domain.entity.TbFinancialSubject;
import com.project.creditmanagementbackend.domain.entity.TbInstallment;
import com.project.creditmanagementbackend.domain.entity.TbLoan;
import com.project.creditmanagementbackend.domain.entity.TbLoanFunding;
import com.project.creditmanagementbackend.domain.enumerado.EnumInstallmentStatus;
import com.project.creditmanagementbackend.domain.enumerado.EnumLoanFundingStatus;
import com.project.creditmanagementbackend.domain.enumerado.EnumLoanStatus;
import com.project.creditmanagementbackend.domain.mapper.TbLoanMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class TbLoanServiceImpl
        extends GenericServiceImpl<TbLoanDto, TbLoan, Long, TbLoanMapper>
        implements TbLoanService {

    private final TbLoanDao loanDao;
    private final TbInstallmentDao installmentDao;
    private final TbFinancialSubjectDao subjectDao;
    private final TbLoanFundingDao fundingDao;

    public TbLoanServiceImpl(
            TbLoanDao loanDao,
            TbInstallmentDao installmentDao,
            TbFinancialSubjectDao subjectDao,
            TbLoanMapper loanMapper,
            TbLoanFundingDao fundingDao) {
        super(loanDao, loanMapper);
        this.loanDao = loanDao;
        this.installmentDao = installmentDao;
        this.subjectDao = subjectDao;
        this.fundingDao = fundingDao;
    }

    @Override
    public TbLoanDto createLoan(TbLoanDto dto) {
        TbFinancialSubject borrower = subjectDao.findById(dto.getBorrowerSubjectId())
                .orElseThrow(() -> new BusinessException(GeneralErrors.ENTITY_NOT_FOUND));

        TbLoan loan = mapper.toEntity(dto);
        loan.setBorrowerSubject(borrower);
        loan.setStatus(EnumLoanStatus.ACTIVO);

        // Cálculo de totales
        BigDecimal rate = dto.getInterestRate().divide(new BigDecimal("100"), 6, RoundingMode.HALF_UP);
        int n = dto.getTermMonths();
        BigDecimal principal = dto.getPrincipalAmount();

        // Sistema francés - cuota fija
        BigDecimal monthlyRate = rate.divide(new BigDecimal("12"), 8, RoundingMode.HALF_UP);
        BigDecimal cuota = calcCuotaFija(principal, monthlyRate, n);

        loan.setTotalToPay(cuota.multiply(new BigDecimal(n)).setScale(2, RoundingMode.HALF_UP));
        loan.setTotalInterest(loan.getTotalToPay().subtract(principal));

        LocalDate start = dto.getStartDate();
        loan.setEndDate(start.plusMonths(n));

        loan = loanDao.save(loan);

        // Fondeos
        if (dto.getFundings() != null && !dto.getFundings().isEmpty()) {
            for (TbLoanFundingDto f : dto.getFundings()) {
                TbFinancialSubject investor = subjectDao.findById(f.getInvestorSubjectId())
                        .orElseThrow(() -> new BusinessException(GeneralErrors.ENTITY_NOT_FOUND));

                TbLoanFunding funding = new TbLoanFunding();
                funding.setLoan(loan);
                funding.setInvestorSubject(investor);
                funding.setFundedAmount(f.getFundedAmount());
                funding.setFundedPercentage(f.getFundedPercentage());
                funding.setExpectedRate(f.getExpectedRate());
                funding.setStatus(EnumLoanFundingStatus.ACTIVO);
                fundingDao.save(funding);
            }
        }

        // Generar cronograma
        generateInstallments(loan, monthlyRate, n, cuota, start);

        return mapper.toDto(loanDao.findById(loan.getId()).orElseThrow());
    }

    @Override
    @Transactional(readOnly = true)
    public TbLoanDto getFullLoan(Long id) {
        TbLoan loan = loanDao.findById(id)
                .orElseThrow(() -> new BusinessException(GeneralErrors.ENTITY_NOT_FOUND));
        return mapper.toDto(loan);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TbLoanDto> findByBorrowerSubject(Long subjectId) {
        return loanDao.findByBorrowerSubjectId(subjectId).stream()
                .map(mapper::toBasicDto)
                .toList();
    }

    @Override
    public TbLoanDto calculate(TbLoanDto dto) {
        BigDecimal rate = dto.getInterestRate().divide(new BigDecimal("100"), 6, RoundingMode.HALF_UP);
        BigDecimal monthlyRate = rate.divide(new BigDecimal("12"), 8, RoundingMode.HALF_UP);
        int n = dto.getTermMonths();
        BigDecimal principal = dto.getPrincipalAmount();

        BigDecimal cuota = calcCuotaFija(principal, monthlyRate, n);
        BigDecimal total = cuota.multiply(new BigDecimal(n)).setScale(2, RoundingMode.HALF_UP);
        BigDecimal interest = total.subtract(principal);

        dto.setMonthlyPayment(cuota);
        dto.setTotalToPay(total);
        dto.setTotalInterest(interest);
        if (dto.getStartDate() != null) {
            dto.setEndDate(dto.getStartDate().plusMonths(n));
        }
        return dto;
    }

    private void generateInstallments(TbLoan loan, BigDecimal monthlyRate, int n,
                                      BigDecimal cuota, LocalDate startDate) {
        BigDecimal remaining = loan.getPrincipalAmount();

        for (int i = 1; i <= n; i++) {
            BigDecimal interest = remaining.multiply(monthlyRate).setScale(2, RoundingMode.HALF_UP);
            BigDecimal principalPart = cuota.subtract(interest).setScale(2, RoundingMode.HALF_UP);

            // Ajuste de la última cuota para evitar diferencias por redondeo
            if (i == n) {
                principalPart = remaining;
                cuota = principalPart.add(interest);
            }

            TbInstallment installment = TbInstallment.builder()
                    .loan(loan)
                    .installmentNumber(i)
                    .dueDate(startDate.plusMonths(i))
                    .principalAmount(principalPart)
                    .interestAmount(interest)
                    .totalAmount(cuota)
                    .paidAmount(BigDecimal.ZERO)
                    .status(EnumInstallmentStatus.PENDIENTE)
                    .daysLate(0)
                    .build();

            installmentDao.save(installment);
            remaining = remaining.subtract(principalPart);
        }
    }

    private BigDecimal calcCuotaFija(BigDecimal principal, BigDecimal i, int n) {
        if (i.compareTo(BigDecimal.ZERO) == 0) {
            return principal.divide(new BigDecimal(n), 2, RoundingMode.HALF_UP);
        }
        BigDecimal onePlusI = BigDecimal.ONE.add(i);
        BigDecimal pow = onePlusI.pow(n);
        BigDecimal numerator = principal.multiply(i).multiply(pow);
        BigDecimal denominator = pow.subtract(BigDecimal.ONE);
        return numerator.divide(denominator, 2, RoundingMode.HALF_UP);
    }
}