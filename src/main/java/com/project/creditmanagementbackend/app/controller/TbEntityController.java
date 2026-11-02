package com.project.creditmanagementbackend.app.controller;

import com.project.creditmanagementbackend.app.service.TbEntityService;
import com.project.creditmanagementbackend.app.util.controller.GenericController;
import com.project.creditmanagementbackend.app.util.service.GenericService;
import com.project.creditmanagementbackend.domain.dto.TbEntityDto;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/entities")
@RequiredArgsConstructor
@Tag(name = "Entities", description = "Operaciones relacionadas con las entidades (personas/empresas)")
@SecurityRequirement(name = "bearerAuth")
public class TbEntityController extends GenericController<TbEntityDto, Integer> {

    private final TbEntityService entityService;

    @Override
    protected GenericService<TbEntityDto, Integer> getService() {
        return entityService;
    }
}
