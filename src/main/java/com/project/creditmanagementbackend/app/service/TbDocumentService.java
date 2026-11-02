package com.project.creditmanagementbackend.app.service;

import com.project.creditmanagementbackend.app.util.service.GenericService;
import com.project.creditmanagementbackend.domain.dto.TbDocumentDto;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface TbDocumentService extends GenericService<TbDocumentDto, Integer> {
    TbDocumentDto uploadFile(MultipartFile file, String entity, Integer entityId, String documentType);
    List<TbDocumentDto> uploadMultipleFiles(MultipartFile[] files, String entity, Integer entityId, String documentType);
    List<TbDocumentDto> findByEntityAndEntityId(String entity, Integer entityId);
    Resource downloadFile(String filePath);
    String getFileMediaType(String filePath);
}
