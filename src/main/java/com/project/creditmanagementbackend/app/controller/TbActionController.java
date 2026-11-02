package com.project.creditmanagementbackend.app.controller;

import com.project.creditmanagementbackend.app.service.TbActionService;
import com.project.creditmanagementbackend.app.util.controller.GenericController;
import com.project.creditmanagementbackend.app.util.response.ApiResponse;
import com.project.creditmanagementbackend.app.util.service.GenericService;
import com.project.creditmanagementbackend.domain.dto.TbActionDto;
import com.project.creditmanagementbackend.domain.dto.response.ActionDependenciesResponseDto;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/actions")
@RequiredArgsConstructor
@Tag(name = "Actions", description = "Operaciones relacionadas con las actions del sistema")
@SecurityRequirement(name = "bearerAuth")
public class TbActionController extends GenericController<TbActionDto, Integer> {

    private final TbActionService accionService;

    @Override
    protected GenericService<TbActionDto, Integer> getService() {
        return accionService;
    }

    @GetMapping("/{id}/dependencies")
    public ResponseEntity<ApiResponse<ActionDependenciesResponseDto>> checkDependencies(@PathVariable Integer id, HttpServletRequest request) {
        ActionDependenciesResponseDto data = accionService.checkDependencies(id);
        return ResponseEntity.ok(ApiResponse.success(data, "Verificación de dependencias", request.getRequestURI()));
    }
}
