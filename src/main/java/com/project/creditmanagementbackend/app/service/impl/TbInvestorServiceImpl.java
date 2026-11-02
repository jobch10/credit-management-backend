package com.project.creditmanagementbackend.app.service.impl;

import com.project.creditmanagementbackend.app.dao.TbEntityDao;
import com.project.creditmanagementbackend.app.dao.TbInvestorDao;
import com.project.creditmanagementbackend.app.exception.BusinessException;
import com.project.creditmanagementbackend.app.exception.module.EntityErrors;
import com.project.creditmanagementbackend.app.exception.module.InvestorErrors;
import com.project.creditmanagementbackend.app.service.TbEntityService;
import com.project.creditmanagementbackend.app.service.TbInvestorService;
import com.project.creditmanagementbackend.app.util.pagination.model.Operator;
import com.project.creditmanagementbackend.app.util.service.impl.GenericServiceImpl;
import com.project.creditmanagementbackend.domain.dto.TbInvestorDto;
import com.project.creditmanagementbackend.domain.entity.TbEntity;
import com.project.creditmanagementbackend.domain.entity.TbInvestor;
import com.project.creditmanagementbackend.domain.mapper.TbInvestorMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@Transactional
public class TbInvestorServiceImpl
        extends GenericServiceImpl<TbInvestorDto, TbInvestor, Integer, TbInvestorMapper>
        implements TbInvestorService {

    private final TbInvestorDao investorDao;
    private final TbEntityService entityService;
    private final TbEntityDao entityDao;

    public TbInvestorServiceImpl(
            TbInvestorDao repository,
            TbInvestorMapper mapper,
            TbEntityService entityService,
            TbEntityDao entityDao) {
        super(repository, mapper);
        this.investorDao = repository;
        this.entityService = entityService;
        this.entityDao = entityDao;
    }

    @Override
    protected Map<String, Operator> getAllowedFilters() {
        return Map.of(
                "investorCode", Operator.LIKE,
                "status", Operator.EQ);
    }

    @Override
    @Transactional
    public TbInvestorDto insert(TbInvestorDto dto) {
        if (dto.getInvestorCode() == null || dto.getInvestorCode().trim().isEmpty()) {
            String maxCode = investorDao.findMaxInvestorCode();
            int nextId = (maxCode == null) ? 1 : Integer.parseInt(maxCode.substring(4)) + 1;
            dto.setInvestorCode(String.format("INV-%08d", nextId));
        } else if (investorDao.existsByInvestorCode(dto.getInvestorCode())) {
            throw new BusinessException(InvestorErrors.INVESTOR_CODE_ALREADY_EXISTS);
        }

        TbInvestor entity = mapper.toEntity(dto);

        TbEntity tbEntity;
        if (dto.getEntityId() != null) {
            tbEntity = entityDao.findById(dto.getEntityId())
                    .orElseThrow(() -> new BusinessException(EntityErrors.ENTITY_NOT_FOUND));
        } else if (dto.getEntity() != null) {
            tbEntity = entityService.findOrCreateEntity(dto.getEntity());
        } else {
            throw new BusinessException(EntityErrors.ENTITY_NOT_FOUND); 
        }
        entity.setEntity(tbEntity);

        return mapper.toDto(investorDao.save(entity));
    }

    @Override
    @Transactional
    public TbInvestorDto update(Integer id, TbInvestorDto dto) {
        TbInvestor entity = investorDao.findById(id)
                .orElseThrow(() -> new BusinessException(InvestorErrors.INVESTOR_NOT_FOUND));

        if (dto.getInvestorCode() != null && 
            !dto.getInvestorCode().equals(entity.getInvestorCode()) &&
            investorDao.existsByInvestorCode(dto.getInvestorCode())) {
            throw new BusinessException(InvestorErrors.INVESTOR_CODE_ALREADY_EXISTS);
        }

        mapper.updateEntityFromDto(dto, entity);

        if (dto.getEntityId() != null) {
            TbEntity tbEntity = entityDao.findById(dto.getEntityId())
                    .orElseThrow(() -> new BusinessException(EntityErrors.ENTITY_NOT_FOUND));
            entity.setEntity(tbEntity);
        } else if (dto.getEntity() != null) {
            TbEntity tbEntity = entityService.findOrCreateEntity(dto.getEntity());
            entity.setEntity(tbEntity);
        }

        return mapper.toDto(investorDao.save(entity));
    }
}
