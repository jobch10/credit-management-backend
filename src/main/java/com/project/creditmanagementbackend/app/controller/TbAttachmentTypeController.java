package com.project.creditmanagementbackend.app.controller;

import com.project.creditmanagementbackend.app.service.TbAttachmentTypeService;
import com.project.creditmanagementbackend.app.util.controller.GenericController;
import com.project.creditmanagementbackend.app.util.service.GenericService;
import com.project.creditmanagementbackend.domain.dto.TbAttachmentTypeDto;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/attachment-types")
@RequiredArgsConstructor
@Tag(name = "Tipos de Archivo", description = "Gestión de tipos de archivo para archivos")
@SecurityRequirement(name = "bearerAuth")
public class TbAttachmentTypeController extends GenericController<TbAttachmentTypeDto, Integer> {

    private final TbAttachmentTypeService attachmentTypeService;

    @Override
    protected GenericService<TbAttachmentTypeDto, Integer> getService() {
        return attachmentTypeService;
    }
}
