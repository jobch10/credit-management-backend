package com.project.creditmanagementbackend.app.controller;

import com.project.creditmanagementbackend.app.service.TbTypeDocumentService;
import com.project.creditmanagementbackend.app.util.controller.GenericController;
import com.project.creditmanagementbackend.app.util.service.GenericService;
import com.project.creditmanagementbackend.domain.dto.TbTypeDocumentDto;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/type-documents")
@RequiredArgsConstructor
@Tag(name = "Tipo Documentos", description = "Operaciones relacionadas con los tipos de documentos")
@SecurityRequirement(name = "bearerAuth")
public class TbTypeDocumentController extends GenericController<TbTypeDocumentDto, Integer> {

    private final TbTypeDocumentService typeDocumentService;

    @Override
    protected GenericService<TbTypeDocumentDto, Integer> getService() {
        return typeDocumentService;
    }
}
