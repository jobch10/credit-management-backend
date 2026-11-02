package com.project.creditmanagementbackend.app.service.impl;

import com.project.creditmanagementbackend.app.dao.TbFinancialSubjectDao;
import com.project.creditmanagementbackend.app.dao.TbInvestorProfileDao;
import com.project.creditmanagementbackend.app.exception.BusinessException;
import com.project.creditmanagementbackend.app.exception.module.GeneralErrors;
import com.project.creditmanagementbackend.app.service.TbInvestorProfileService;
import com.project.creditmanagementbackend.app.util.service.impl.GenericServiceImpl;
import com.project.creditmanagementbackend.domain.dto.TbInvestorProfileDto;
import com.project.creditmanagementbackend.domain.entity.TbInvestorProfile;
import com.project.creditmanagementbackend.domain.mapper.TbInvestorProfileMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TbInvestorProfileServiceImpl
        extends GenericServiceImpl<TbInvestorProfileDto, TbInvestorProfile, Long, TbInvestorProfileMapper>
        implements TbInvestorProfileService {

    private final TbInvestorProfileDao investorProfileDao;
    private final TbFinancialSubjectDao subjectDao;

    public TbInvestorProfileServiceImpl(
            TbInvestorProfileDao investorProfileDao,
            TbFinancialSubjectDao subjectDao,
            TbInvestorProfileMapper investorProfileMapper) {
        super(investorProfileDao, investorProfileMapper);
        this.investorProfileDao = investorProfileDao;
        this.subjectDao = subjectDao;
    }

    @Override
    @Transactional(readOnly = true)
    public TbInvestorProfileDto findByFinancialSubjectId(Long financialSubjectId) {
        TbInvestorProfile entity = investorProfileDao.findByFinancialSubjectId(financialSubjectId)
                .orElseThrow(() -> new BusinessException(GeneralErrors.ENTITY_NOT_FOUND));
        return mapper.toDto(entity);
    }
}