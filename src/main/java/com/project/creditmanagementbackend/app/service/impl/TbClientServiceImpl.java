package com.project.creditmanagementbackend.app.service.impl;

import com.project.creditmanagementbackend.app.dao.TbClientDao;
import com.project.creditmanagementbackend.app.dao.TbEntityDao;
import com.project.creditmanagementbackend.app.exception.BusinessException;
import com.project.creditmanagementbackend.app.exception.module.ClientErrors;
import com.project.creditmanagementbackend.app.exception.module.EntityErrors;
import com.project.creditmanagementbackend.app.service.TbClientService;
import com.project.creditmanagementbackend.app.service.TbEntityService;
import com.project.creditmanagementbackend.app.util.pagination.model.Operator;
import com.project.creditmanagementbackend.app.util.service.impl.GenericServiceImpl;
import com.project.creditmanagementbackend.domain.dto.TbClientDto;
import com.project.creditmanagementbackend.domain.entity.TbClient;
import com.project.creditmanagementbackend.domain.entity.TbEntity;
import com.project.creditmanagementbackend.domain.mapper.TbClientMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@Transactional
public class TbClientServiceImpl
        extends GenericServiceImpl<TbClientDto, TbClient, Integer, TbClientMapper>
        implements TbClientService {

    private final TbClientDao clientDao;
    private final TbEntityService entityService;
    private final TbEntityDao entityDao;

    public TbClientServiceImpl(
            TbClientDao repository,
            TbClientMapper mapper,
            TbEntityService entityService,
            TbEntityDao entityDao) {
        super(repository, mapper);
        this.clientDao = repository;
        this.entityService = entityService;
        this.entityDao = entityDao;
    }

    @Override
    protected Map<String, Operator> getAllowedFilters() {
        return Map.of(
                "clientCode", Operator.LIKE,
                "riskLevel", Operator.EQ,
                "entity.legalName", Operator.LIKE,
                "state", Operator.EQ);
    }

    @Override
    @Transactional
    public TbClientDto insert(TbClientDto dto) {
        if (dto.getClientCode() == null || dto.getClientCode().trim().isEmpty()) {
            String maxCode = clientDao.findMaxClientCode();
            int nextId = (maxCode == null) ? 1 : Integer.parseInt(maxCode.substring(4)) + 1;
            dto.setClientCode(String.format("CLI-%08d", nextId));
        } else if (clientDao.findByClientCode(dto.getClientCode()).isPresent()) {
            throw new BusinessException(ClientErrors.CLIENT_CODE_ALREADY_EXISTS);
        }

        TbClient entity = mapper.toEntity(dto);

        TbEntity tbEntity;
        if (dto.getEntityId() != null) {
            tbEntity = entityDao.findById(dto.getEntityId())
                    .orElseThrow(() -> new BusinessException(EntityErrors.ENTITY_NOT_FOUND));
        } else if (dto.getEntity() != null) {
            tbEntity = entityService.findOrCreateEntity(dto.getEntity());
        } else {
            throw new BusinessException(ClientErrors.ENTITY_REQUIRED);
        }
        entity.setEntity(tbEntity);

        return mapper.toDto(clientDao.save(entity));
    }

    @Override
    @Transactional
    public TbClientDto update(Integer id, TbClientDto dto) {
        TbClient entity = clientDao.findById(id)
                .orElseThrow(() -> new BusinessException(ClientErrors.CLIENT_NOT_FOUND));

        if (dto.getClientCode() != null && 
            !dto.getClientCode().equals(entity.getClientCode()) &&
            clientDao.findByClientCode(dto.getClientCode()).isPresent()) {
            throw new BusinessException(ClientErrors.CLIENT_CODE_ALREADY_EXISTS);
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

        return mapper.toDto(clientDao.save(entity));
    }
}
