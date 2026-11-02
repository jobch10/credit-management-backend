package com.project.creditmanagementbackend.app.service.impl;

import com.project.creditmanagementbackend.app.dao.TbFinancialSubjectDao;
import com.project.creditmanagementbackend.app.dao.TbLoanDao;
import com.project.creditmanagementbackend.app.dao.TbLoanFundingDao;
import com.project.creditmanagementbackend.app.exception.BusinessException;
import com.project.creditmanagementbackend.app.exception.module.GeneralErrors;
import com.project.creditmanagementbackend.app.service.TbLoanFundingService;
import com.project.creditmanagementbackend.app.util.service.impl.GenericServiceImpl;
import com.project.creditmanagementbackend.domain.dto.TbLoanFundingDto;
import com.project.creditmanagementbackend.domain.entity.TbFinancialSubject;
import com.project.creditmanagementbackend.domain.entity.TbLoan;
import com.project.creditmanagementbackend.domain.entity.TbLoanFunding;
import com.project.creditmanagementbackend.domain.enumerado.EnumLoanFundingStatus;
import com.project.creditmanagementbackend.domain.mapper.TbLoanFundingMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TbLoanFundingServiceImpl
        extends GenericServiceImpl<TbLoanFundingDto, TbLoanFunding, Long, TbLoanFundingMapper>
        implements TbLoanFundingService {

    private final TbLoanFundingDao fundingDao;
    private final TbLoanDao loanDao;
    private final TbFinancialSubjectDao subjectDao;

    public TbLoanFundingServiceImpl(
            TbLoanFundingDao fundingDao,
            TbLoanDao loanDao,
            TbFinancialSubjectDao subjectDao,
            TbLoanFundingMapper fundingMapper) {
        super(fundingDao, fundingMapper);
        this.fundingDao = fundingDao;
        this.loanDao = loanDao;
        this.subjectDao = subjectDao;
    }

    @Override
    public TbLoanFundingDto addFunding(Long loanId, TbLoanFundingDto dto) {
        TbLoan loan = loanDao.findById(loanId)
                .orElseThrow(() -> new BusinessException(GeneralErrors.ENTITY_NOT_FOUND));
        TbFinancialSubject investor = subjectDao.findById(dto.getInvestorSubjectId())
                .orElseThrow(() -> new BusinessException(GeneralErrors.ENTITY_NOT_FOUND));

        TbLoanFunding funding = mapper.toEntity(dto);
        funding.setLoan(loan);
        funding.setInvestorSubject(investor);
        funding.setStatus(EnumLoanFundingStatus.ACTIVO);

        return mapper.toDto(fundingDao.save(funding));
    }

    @Override
    @Transactional(readOnly = true)
    public List<TbLoanFundingDto> findByLoan(Long loanId) {
        return fundingDao.findByLoanId(loanId).stream()
                .map(mapper::toBasicDto)
                .toList();
    }
}