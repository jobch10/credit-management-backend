package com.project.creditmanagementbackend.app.controller;

import com.project.creditmanagementbackend.app.util.response.ApiResponse;
import com.project.creditmanagementbackend.domain.dto.request.UpdateModuleActionsRequestDto;
import com.project.creditmanagementbackend.domain.dto.response.ModuleDependenciesResponseDto;
import com.project.creditmanagementbackend.domain.enumerado.ModuleType;
import jakarta.servlet.http.HttpServletRequest;
import com.project.creditmanagementbackend.app.service.TbModuleService;
import com.project.creditmanagementbackend.app.util.controller.GenericController;
import com.project.creditmanagementbackend.app.util.service.GenericService;
import com.project.creditmanagementbackend.domain.dto.TbModuleDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/modules")
@RequiredArgsConstructor
@Tag(name = "Módulos", description = "Operaciones relacionadas con los módulos del sistema")
@SecurityRequirement(name = "bearerAuth")
public class TbModuleController extends GenericController<TbModuleDto, Integer> {

    private final TbModuleService moduloService;

    @Override
    protected GenericService<TbModuleDto, Integer> getService() {
        return moduloService;
    }

    @Operation(summary = "Actualizar acciones de un módulo", description = "Reemplaza las acciones asignadas al módulo por las enviadas en la solicitud")
    @PutMapping("/{id}/actions")
    public ResponseEntity<ApiResponse<TbModuleDto>> updateModuleActions(@PathVariable Integer id, @Valid @RequestBody UpdateModuleActionsRequestDto request, HttpServletRequest httpRequest) {
        TbModuleDto moduleUpdated = moduloService.updateModuleActions(id, request.getActionIds());
        return ResponseEntity.ok(ApiResponse.success(moduleUpdated, "Acciones del módulo actualizadas correctamente", httpRequest.getRequestURI()));
    }

    @GetMapping("/{id}/action-ids")
    public ResponseEntity<ApiResponse<List<Long>>> getActionIdsByModule(@PathVariable Integer id, HttpServletRequest request) {
        List<Long> actionIds = moduloService.getActionIdsByModule(id);
        return ResponseEntity.ok(ApiResponse.success(actionIds, "IDs de acciones obtenidos correctamente", request.getRequestURI()));
    }

    @GetMapping("/by-type")
    public ResponseEntity<ApiResponse<List<TbModuleDto>>> getModulesByType(@RequestParam(required = false) ModuleType type, HttpServletRequest request) {
        List<TbModuleDto> modules = moduloService.getModulesByType(type);
        return ResponseEntity.ok(ApiResponse.success(modules, "Módulos obtenidos correctamente", request.getRequestURI()));
    }
    @GetMapping("/with-actions")
    public ResponseEntity<ApiResponse<List<TbModuleDto>>> getModulesWithActions(HttpServletRequest request) {
        List<TbModuleDto> modules = moduloService.getModulesWithActions();
        return ResponseEntity.ok(ApiResponse.success(modules, "Módulos solo con acciones obtenidos correctamente", request.getRequestURI()));
    }

    @GetMapping("/{id}/dependencies")
    public ResponseEntity<ApiResponse<ModuleDependenciesResponseDto>> checkDependencies(@PathVariable Integer id, HttpServletRequest request) {
        ModuleDependenciesResponseDto data = moduloService.checkDependencies(id);
        return ResponseEntity.ok(ApiResponse.success(data, "Verificación de dependencias del módulo", request.getRequestURI()));
    }
}
