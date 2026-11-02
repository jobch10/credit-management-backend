package com.project.creditmanagementbackend.app.service.impl;

import com.project.creditmanagementbackend.app.dao.TbEntityDao;
import com.project.creditmanagementbackend.app.dao.TbTypeDocumentDao;
import com.project.creditmanagementbackend.app.exception.BusinessException;
import com.project.creditmanagementbackend.app.exception.module.EntityErrors;
import com.project.creditmanagementbackend.app.service.TbEntityService;
import com.project.creditmanagementbackend.app.util.pagination.model.Operator;
import com.project.creditmanagementbackend.app.util.service.impl.GenericServiceImpl;
import com.project.creditmanagementbackend.domain.dto.TbEntityDto;
import com.project.creditmanagementbackend.domain.entity.TbEntity;
import com.project.creditmanagementbackend.domain.entity.TbTypeDocument;
import com.project.creditmanagementbackend.domain.mapper.TbEntityMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@Transactional
public class TbEntityServiceImpl
        extends GenericServiceImpl<TbEntityDto, TbEntity, Integer, TbEntityMapper>
        implements TbEntityService {

    private final TbEntityDao entityDao;
    private final TbTypeDocumentDao typeDocumentDao;

    public TbEntityServiceImpl(
            TbEntityDao repository,
            TbEntityMapper mapper,
            TbTypeDocumentDao typeDocumentDao) {
        super(repository, mapper);
        this.entityDao = repository;
        this.typeDocumentDao = typeDocumentDao;
    }

    @Override
    protected Map<String, Operator> getAllowedFilters() {
        return Map.of(
                "legalName", Operator.LIKE,
                "documentNumber", Operator.LIKE,
                "entityType", Operator.EQ,
                "state", Operator.EQ);
    }

    @Override
    @Transactional
    public TbEntityDto insert(TbEntityDto dto) {
        if (entityDao.findByDocumentNumber(dto.getDocumentNumber()).isPresent()) {
            throw new BusinessException(EntityErrors.DOCUMENT_NUMBER_ALREADY_EXISTS);
        }

        TbEntity entity = mapper.toEntity(dto);
        
        if (dto.getTypeDocumentId() != null) {
            TbTypeDocument td = typeDocumentDao.findById(dto.getTypeDocumentId())
                    .orElseThrow(() -> new BusinessException(EntityErrors.TIPO_DOCUMENTO_NOT_FOUND));
            entity.setTypeDocument(td);
        }

        return mapper.toDto(entityDao.save(entity));
    }

    @Override
    @Transactional
    public TbEntityDto update(Integer id, TbEntityDto dto) {
        TbEntity entity = entityDao.findById(id)
                .orElseThrow(() -> new BusinessException(EntityErrors.ENTITY_NOT_FOUND));

        if (dto.getDocumentNumber() != null && 
            !dto.getDocumentNumber().equals(entity.getDocumentNumber()) &&
            entityDao.findByDocumentNumber(dto.getDocumentNumber()).isPresent()) {
            throw new BusinessException(EntityErrors.DOCUMENT_NUMBER_ALREADY_EXISTS);
        }

        mapper.updateEntityFromDto(dto, entity);

        if (dto.getTypeDocumentId() != null) {
            TbTypeDocument td = typeDocumentDao.findById(dto.getTypeDocumentId())
                    .orElseThrow(() -> new BusinessException(EntityErrors.TIPO_DOCUMENTO_NOT_FOUND));
            entity.setTypeDocument(td);
        }

        return mapper.toDto(entityDao.save(entity));
    }
    @Override
    @Transactional
    public TbEntity findOrCreateEntity(TbEntityDto dto) {
        if (dto.getDocumentNumber() == null) {
            throw new BusinessException(EntityErrors.DOCUMENT_NUMBER_REQUIRED);
        }

        return entityDao.findByDocumentNumber(dto.getDocumentNumber())
                .orElseGet(() -> {
                    TbEntity newEntity = mapper.toEntity(dto);
                    if (dto.getTypeDocumentId() != null) {
                        TbTypeDocument td = typeDocumentDao.findById(dto.getTypeDocumentId())
                                .orElseThrow(() -> new BusinessException(EntityErrors.TIPO_DOCUMENTO_NOT_FOUND));
                        newEntity.setTypeDocument(td);
                    } else {
                        throw new BusinessException(EntityErrors.TIPO_DOCUMENTO_REQUIRED);
                    }
                    return entityDao.save(newEntity);
                });
    }
}
