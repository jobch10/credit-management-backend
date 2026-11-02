package com.project.creditmanagementbackend.app.controller;

import com.project.creditmanagementbackend.app.service.TbLoanFundingService;
import com.project.creditmanagementbackend.app.util.controller.GenericController;
import com.project.creditmanagementbackend.app.util.response.ApiResponse;
import com.project.creditmanagementbackend.app.util.service.GenericService;
import com.project.creditmanagementbackend.domain.dto.TbLoanFundingDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/loan-fundings")
@RequiredArgsConstructor
@Tag(name = "Fondeos de Crédito")
@SecurityRequirement(name = "bearerAuth")
public class TbLoanFundingController extends GenericController<TbLoanFundingDto, Long> {

    private final TbLoanFundingService fundingService;

    @Override
    protected GenericService<TbLoanFundingDto, Long> getService() {
        return fundingService;
    }

    @PostMapping("/loan/{loanId}")
    @Operation(summary = "Registrar fondeo de un inversionista")
    public ResponseEntity<ApiResponse<TbLoanFundingDto>> addFunding(
            @PathVariable Long loanId,
            @Valid @RequestBody TbLoanFundingDto dto,
            HttpServletRequest request) {
        TbLoanFundingDto data = fundingService.addFunding(loanId, dto);
        return ResponseEntity.status(201)
                .body(ApiResponse.created(data, "Fondeo registrado", request.getRequestURI()));
    }

    @GetMapping("/by-loan/{loanId}")
    @Operation(summary = "Fondeos por crédito")
    public ResponseEntity<ApiResponse<List<TbLoanFundingDto>>> byLoan(
            @PathVariable Long loanId, HttpServletRequest request) {
        List<TbLoanFundingDto> data = fundingService.findByLoan(loanId);
        return ResponseEntity.ok(ApiResponse.success(data, "Listado", request.getRequestURI()));
    }
}