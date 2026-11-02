package com.project.creditmanagementbackend.app.controller;

import com.project.creditmanagementbackend.app.service.TbRoleService;
import com.project.creditmanagementbackend.app.util.controller.GenericController;
import com.project.creditmanagementbackend.app.util.response.ApiResponse;
import com.project.creditmanagementbackend.app.util.service.GenericService;
import com.project.creditmanagementbackend.domain.dto.TbRoleDto;
import com.project.creditmanagementbackend.domain.dto.request.PermisoRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
@Tag(name = "Roles", description = "Gestión de roles y asignación de permisos masivos.")
@SecurityRequirement(name = "bearerAuth")
public class TbRoleController extends GenericController<TbRoleDto, Integer> {

    private final TbRoleService rolService;

    @Override
    protected GenericService<TbRoleDto, Integer> getService() {
        return rolService;
    }

    @PostMapping("/{rolId}/permisos")
    @Operation(summary = "Asignar permisos a un role", description = "Actualiza los permisos del role mediante módulos y actions")
    public ResponseEntity<ApiResponse<TbRoleDto>> assignPermissions(@PathVariable("rolId") Integer rolId, @RequestBody List<PermisoRequestDto> permisosRequest, HttpServletRequest request) {
        TbRoleDto rolDto = rolService.updateRoleModules(rolId, permisosRequest);
        return ResponseEntity.ok(ApiResponse.success(rolDto, "Permisos actualizados correctamente", request.getRequestURI()));
    }

    @GetMapping("/{rolId}/permisos")
    @Operation(summary = "Obtener permisos de un rol", description = "Retorna el rol con sus módulos y acciones asignadas")
    public ResponseEntity<ApiResponse<TbRoleDto>> getRolePermissions(@PathVariable("rolId") Integer rolId, HttpServletRequest request) {
        TbRoleDto rolDto = rolService.getRolePermissions(rolId);
        return ResponseEntity.ok(ApiResponse.success(rolDto, "Permisos del rol obtenidos correctamente", request.getRequestURI()));
    }

}
