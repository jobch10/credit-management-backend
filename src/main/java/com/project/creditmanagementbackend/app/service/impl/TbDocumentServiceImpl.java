package com.project.creditmanagementbackend.app.service.impl;

import com.project.creditmanagementbackend.app.dao.TbDocumentDao;
import com.project.creditmanagementbackend.app.exception.BusinessException;
import com.project.creditmanagementbackend.app.exception.module.GeneralErrors;
import com.project.creditmanagementbackend.app.service.TbDocumentService;
import com.project.creditmanagementbackend.app.util.service.impl.GenericServiceImpl;
import com.project.creditmanagementbackend.app.util.storage.FileStorageService;
import com.project.creditmanagementbackend.domain.dto.TbDocumentDto;
import com.project.creditmanagementbackend.domain.entity.TbDocument;
import com.project.creditmanagementbackend.domain.mapper.TbDocumentMapper;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class TbDocumentServiceImpl extends GenericServiceImpl<TbDocumentDto, TbDocument, Integer, TbDocumentMapper> implements TbDocumentService {

    private final TbDocumentDao documentDao;
    private final FileStorageService fileStorageService;

    public TbDocumentServiceImpl(TbDocumentDao repository, TbDocumentMapper mapper, FileStorageService fileStorageService) {
        super(repository, mapper);
        this.documentDao = repository;
        this.fileStorageService = fileStorageService;
    }

    @Override
    public TbDocumentDto uploadFile(MultipartFile file, String entity, Integer entityId, String documentType) {
        String subFolder = entity + "/" + entityId;
        String fileUrl = fileStorageService.storeFile(file, subFolder);

        TbDocument document = TbDocument.builder()
                .entity(entity)
                .entityId(entityId)
                .documentType(documentType)
                .fileName(file.getOriginalFilename())
                .fileUrl(fileUrl)
                .createdAt(LocalDateTime.now())
                .build();

        return mapper.toDto(documentDao.save(document));
    }

    @Override
    public List<TbDocumentDto> uploadMultipleFiles(MultipartFile[] files, String entity, Integer entityId, String documentType) {
        return java.util.Arrays.stream(files)
                .map(file -> uploadFile(file, entity, entityId, documentType))
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<TbDocumentDto> findByEntityAndEntityId(String entity, Integer entityId) {
        return documentDao.findByEntityAndEntityId(entity, entityId)
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        TbDocument document = repository.findById(id)
                .orElseThrow(() -> new BusinessException(GeneralErrors.ENTITY_NOT_FOUND));

        fileStorageService.deleteFile(document.getFileUrl());
        repository.deleteById(id);
    }

    @Override
    public Resource downloadFile(String filePath) {
        return fileStorageService.loadFileAsResource(filePath);
    }

    @Override
    public String getFileMediaType(String filePath) {
        return fileStorageService.getFileMediaType(filePath);
    }
}
