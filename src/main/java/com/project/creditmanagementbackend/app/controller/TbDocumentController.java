package com.project.creditmanagementbackend.app.controller;

import com.project.creditmanagementbackend.app.service.TbDocumentService;
import com.project.creditmanagementbackend.app.util.controller.GenericController;
import com.project.creditmanagementbackend.app.util.response.ApiResponse;
import com.project.creditmanagementbackend.app.util.service.GenericService;
import com.project.creditmanagementbackend.domain.dto.TbDocumentDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/documents")
@RequiredArgsConstructor
@Tag(name = "Documentos", description = "Operaciones relacionadas con la gestión de documentos")
@SecurityRequirement(name = "bearerAuth")
public class TbDocumentController extends GenericController<TbDocumentDto, Integer> {

    private final TbDocumentService documentService;

    @Override
    protected GenericService<TbDocumentDto, Integer> getService() {
        return documentService;
    }

    @Operation(summary = "Carga archivos para una entidad específica")
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<List<TbDocumentDto>>> uploadFiles(
            @RequestParam("files") MultipartFile[] files,
            @RequestParam("entity") String entity,
            @RequestParam("entityId") Integer entityId,
            @RequestParam(value = "documentType", required = false) String documentType,
            HttpServletRequest request) {

        List<TbDocumentDto> data = documentService.uploadMultipleFiles(files, entity, entityId, documentType);

        return ResponseEntity.status(201)
                .body(ApiResponse.created(data, "Archivos cargados con éxito", request.getRequestURI()));
    }

    @Operation(summary = "Obtiene los documentos por entidad y ID")
    @GetMapping("/entity/{entity}/{entityId}")
    public ResponseEntity<ApiResponse<List<TbDocumentDto>>> getByEntity(
            @PathVariable String entity,
            @PathVariable Integer entityId,
            HttpServletRequest request) {

        List<TbDocumentDto> data = documentService.findByEntityAndEntityId(entity, entityId);

        return ResponseEntity.ok(ApiResponse.success(data, "Obtenidos los documentos por entidad y ID", request.getRequestURI()));
    }

    @Operation(summary = "Descarga un archivo específico")
    @GetMapping("/download")
    public ResponseEntity<Resource> downloadFile(
            @RequestParam("filePath") String filePath) {

        Resource resource = documentService.downloadFile(filePath);

        String contentType = documentService.getFileMediaType(resource.getFilename());
        if (contentType == null) {
            contentType = "application/octet-stream"; // Default content type if not determined
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}
