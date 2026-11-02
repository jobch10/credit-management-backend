package com.project.creditmanagementbackend.app.service.impl;

import com.project.creditmanagementbackend.app.dao.TbInstallmentDao;
import com.project.creditmanagementbackend.app.service.TbInstallmentService;
import com.project.creditmanagementbackend.app.util.service.impl.GenericServiceImpl;
import com.project.creditmanagementbackend.domain.dto.TbInstallmentDto;
import com.project.creditmanagementbackend.domain.entity.TbInstallment;
import com.project.creditmanagementbackend.domain.enumerado.EnumInstallmentStatus;
import com.project.creditmanagementbackend.domain.mapper.TbInstallmentMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TbInstallmentServiceImpl
        extends GenericServiceImpl<TbInstallmentDto, TbInstallment, Long, TbInstallmentMapper>
        implements TbInstallmentService {

    private final TbInstallmentDao installmentDao;

    public TbInstallmentServiceImpl(
            TbInstallmentDao installmentDao,
            TbInstallmentMapper installmentMapper) {
        super(installmentDao, installmentMapper);
        this.installmentDao = installmentDao;
    }

    @Override
    @Transactional(readOnly = true)
    public List<TbInstallmentDto> findByLoan(Long loanId) {
        return installmentDao.findByLoanIdOrderByInstallmentNumberAsc(loanId).stream()
                .map(mapper::toBasicDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<TbInstallmentDto> findPendingByLoan(Long loanId) {
        return installmentDao.findByLoanIdAndStatus(loanId, EnumInstallmentStatus.PENDIENTE).stream()
                .map(mapper::toBasicDto)
                .toList();
    }
}