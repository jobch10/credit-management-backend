package com.project.creditmanagementbackend.app.service.impl;

import com.project.creditmanagementbackend.app.dao.TbAttachmentTypeDao;
import com.project.creditmanagementbackend.app.dao.TbDocumentDao;
import com.project.creditmanagementbackend.app.exception.BusinessException;
import com.project.creditmanagementbackend.app.exception.module.AttachmentTypeErrors;
import com.project.creditmanagementbackend.app.service.TbAttachmentTypeService;
import com.project.creditmanagementbackend.app.util.service.impl.GenericServiceImpl;
import com.project.creditmanagementbackend.domain.dto.TbAttachmentTypeDto;
import com.project.creditmanagementbackend.domain.entity.TbAttachmentType;
import com.project.creditmanagementbackend.domain.mapper.TbAttachmentTypeMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TbAttachmentTypeServiceImpl extends GenericServiceImpl<TbAttachmentTypeDto, TbAttachmentType, Integer, TbAttachmentTypeMapper> implements TbAttachmentTypeService {

    private final TbAttachmentTypeDao attachmentTypeDao;
    private final TbDocumentDao documentDao;

    public TbAttachmentTypeServiceImpl(TbAttachmentTypeDao repository, TbAttachmentTypeMapper mapper, TbDocumentDao documentDao) {
        super(repository, mapper);
        this.attachmentTypeDao = repository;
        this.documentDao = documentDao;
    }

    @Override
    public TbAttachmentTypeDto insert(TbAttachmentTypeDto dto) {
        if (attachmentTypeDao.existsByCode(dto.getCode())) {
            throw new BusinessException(AttachmentTypeErrors.ATTACHMENT_TYPE_CODE_ALREADY_EXISTS);
        }
        return super.insert(dto);
    }

    @Override
    public void delete(Integer id) {
        TbAttachmentType attachmentType = attachmentTypeDao.findById(id)
                .orElseThrow(() -> new BusinessException(AttachmentTypeErrors.ATTACHMENT_TYPE_NOT_FOUND));

        if (documentDao.existsByDocumentType(attachmentType.getCode())) {
            throw new BusinessException(AttachmentTypeErrors.ATTACHMENT_TYPE_IN_USE);
        }
        super.delete(id);
    }
}