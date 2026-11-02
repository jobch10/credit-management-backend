package com.project.creditmanagementbackend.app.controller;

import com.project.creditmanagementbackend.app.service.TbInvestorProfileService;
import com.project.creditmanagementbackend.app.util.controller.GenericController;
import com.project.creditmanagementbackend.app.util.response.ApiResponse;
import com.project.creditmanagementbackend.app.util.service.GenericService;
import com.project.creditmanagementbackend.domain.dto.TbInvestorProfileDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/investor-profiles")
@RequiredArgsConstructor
@Tag(name = "Perfiles de Inversionista", description = "Gestión del perfil de inversionistas")
@SecurityRequirement(name = "bearerAuth")
public class TbInvestorProfileController extends GenericController<TbInvestorProfileDto, Long> {

    private final TbInvestorProfileService investorProfileService;

    @Override
    protected GenericService<TbInvestorProfileDto, Long> getService() {
        return investorProfileService;
    }

    @GetMapping("/by-subject/{subjectId}")
    @Operation(summary = "Obtener el perfil de inversionista por sujeto")
    public ResponseEntity<ApiResponse<TbInvestorProfileDto>> findBySubject(
            @PathVariable Long subjectId, HttpServletRequest request) {
        TbInvestorProfileDto data = investorProfileService.findByFinancialSubjectId(subjectId);
        return ResponseEntity.ok(ApiResponse.success(data, "Perfil obtenido", request.getRequestURI()));
    }
}