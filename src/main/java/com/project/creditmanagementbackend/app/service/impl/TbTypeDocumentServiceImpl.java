package com.project.creditmanagementbackend.app.service.impl;

import com.project.creditmanagementbackend.app.dao.TbTypeDocumentDao;
import com.project.creditmanagementbackend.app.service.TbTypeDocumentService;
import com.project.creditmanagementbackend.app.util.pagination.model.Operator;
import com.project.creditmanagementbackend.app.util.service.impl.GenericServiceImpl;
import com.project.creditmanagementbackend.domain.dto.TbTypeDocumentDto;
import com.project.creditmanagementbackend.domain.entity.TbTypeDocument;
import com.project.creditmanagementbackend.domain.mapper.TbTypeDocumentMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@Transactional
public class TbTypeDocumentServiceImpl
        extends GenericServiceImpl<TbTypeDocumentDto, TbTypeDocument, Integer, TbTypeDocumentMapper>
        implements TbTypeDocumentService {

    public TbTypeDocumentServiceImpl(
            TbTypeDocumentDao repository,
            TbTypeDocumentMapper mapper) {
        super(repository, mapper);
    }

    @Override
    protected Map<String, Operator> getAllowedFilters() {
        return Map.of(
                "name", Operator.LIKE,
                "code", Operator.LIKE,
                "state", Operator.EQ);
    }
}
