package com.project.creditmanagementbackend.app.controller;

import com.project.creditmanagementbackend.app.service.TbInstallmentService;
import com.project.creditmanagementbackend.app.util.controller.GenericController;
import com.project.creditmanagementbackend.app.util.response.ApiResponse;
import com.project.creditmanagementbackend.app.util.service.GenericService;
import com.project.creditmanagementbackend.domain.dto.TbInstallmentDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/installments")
@RequiredArgsConstructor
@Tag(name = "Cuotas")
@SecurityRequirement(name = "bearerAuth")
public class TbInstallmentController extends GenericController<TbInstallmentDto, Long> {

    private final TbInstallmentService installmentService;

    @Override
    protected GenericService<TbInstallmentDto, Long> getService() {
        return installmentService;
    }

    @GetMapping("/by-loan/{loanId}")
    @Operation(summary = "Cuotas de un crédito")
    public ResponseEntity<ApiResponse<List<TbInstallmentDto>>> byLoan(
            @PathVariable Long loanId, HttpServletRequest request) {
        List<TbInstallmentDto> data = installmentService.findByLoan(loanId);
        return ResponseEntity.ok(ApiResponse.success(data, "Cronograma", request.getRequestURI()));
    }

    @GetMapping("/pending/by-loan/{loanId}")
    @Operation(summary = "Cuotas pendientes de un crédito")
    public ResponseEntity<ApiResponse<List<TbInstallmentDto>>> pending(
            @PathVariable Long loanId, HttpServletRequest request) {
        List<TbInstallmentDto> data = installmentService.findPendingByLoan(loanId);
        return ResponseEntity.ok(ApiResponse.success(data, "Cuotas pendientes", request.getRequestURI()));
    }
}