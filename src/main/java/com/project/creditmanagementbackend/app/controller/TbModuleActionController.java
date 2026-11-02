package com.project.creditmanagementbackend.app.controller;

import com.project.creditmanagementbackend.app.service.TbModuleActionService;
import com.project.creditmanagementbackend.app.util.response.ApiResponse;
import com.project.creditmanagementbackend.domain.dto.request.PermisoRequestDto;
import com.project.creditmanagementbackend.domain.dto.TbModuleDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/modulos/actions")
@RequiredArgsConstructor
@Tag(name = "Módulos Actions", description = "Asignación masiva de actions a módulos")
@SecurityRequirement(name = "bearerAuth")
public class TbModuleActionController {

    private final TbModuleActionService moduloAccionService;

    @PostMapping
    @Operation(summary = "Asignar actions a módulos", description = "Actualiza el set de actions para cada módulo enviado")
    public ResponseEntity<ApiResponse<List<TbModuleDto>>> updateModuleActions(
            @RequestBody List<PermisoRequestDto> asignaciones,
            HttpServletRequest request) {

        List<TbModuleDto> modules = moduloAccionService.updateModuleActions(asignaciones);

        return ResponseEntity.ok(ApiResponse.success(modules, "Actions de módulos actualizadas correctamente", request.getRequestURI()));
    }
}
