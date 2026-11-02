package com.project.creditmanagementbackend.app.service.impl;

import com.project.creditmanagementbackend.app.dao.TbCreditProfileDao;
import com.project.creditmanagementbackend.app.exception.BusinessException;
import com.project.creditmanagementbackend.app.exception.module.GeneralErrors;
import com.project.creditmanagementbackend.app.service.TbCreditProfileService;
import com.project.creditmanagementbackend.app.util.service.impl.GenericServiceImpl;
import com.project.creditmanagementbackend.domain.dto.TbCreditProfileDto;
import com.project.creditmanagementbackend.domain.entity.TbCreditProfile;
import com.project.creditmanagementbackend.domain.mapper.TbCreditProfileMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class TbCreditProfileServiceImpl
        extends GenericServiceImpl<TbCreditProfileDto, TbCreditProfile, Long, TbCreditProfileMapper>
        implements TbCreditProfileService {

    private final TbCreditProfileDao creditProfileDao;

    public TbCreditProfileServiceImpl(
            TbCreditProfileDao creditProfileDao,
            TbCreditProfileMapper creditProfileMapper) {
        super(creditProfileDao, creditProfileMapper);
        this.creditProfileDao = creditProfileDao;
    }

    @Override
    @Transactional(readOnly = true)
    public TbCreditProfileDto findByFinancialSubjectId(Long financialSubjectId) {
        TbCreditProfile entity = creditProfileDao.findByFinancialSubjectId(financialSubjectId)
                .orElseThrow(() -> new BusinessException(GeneralErrors.ENTITY_NOT_FOUND));
        return mapper.toDto(entity);
    }
}