package com.project.creditmanagementbackend.app.controller;

import com.project.creditmanagementbackend.app.service.TbCreditProfileService;
import com.project.creditmanagementbackend.app.util.controller.GenericController;
import com.project.creditmanagementbackend.app.util.response.ApiResponse;
import com.project.creditmanagementbackend.app.util.service.GenericService;
import com.project.creditmanagementbackend.domain.dto.TbCreditProfileDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/credit-profiles")
@RequiredArgsConstructor
@Tag(name = "Perfiles de Crédito", description = "Evaluación y gestión del perfil crediticio")
@SecurityRequirement(name = "bearerAuth")
public class TbCreditProfileController extends GenericController<TbCreditProfileDto, Long> {

    private final TbCreditProfileService creditProfileService;

    @Override
    protected GenericService<TbCreditProfileDto, Long> getService() {
        return creditProfileService;
    }

    @GetMapping("/by-subject/{subjectId}")
    @Operation(summary = "Obtener el perfil de crédito por sujeto")
    public ResponseEntity<ApiResponse<TbCreditProfileDto>> findBySubject(
            @PathVariable Long subjectId, HttpServletRequest request) {
        TbCreditProfileDto data = creditProfileService.findByFinancialSubjectId(subjectId);
        return ResponseEntity.ok(ApiResponse.success(data, "Perfil obtenido", request.getRequestURI()));
    }
}