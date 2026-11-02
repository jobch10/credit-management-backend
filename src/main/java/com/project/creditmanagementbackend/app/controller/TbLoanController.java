package com.project.creditmanagementbackend.app.controller;

import com.project.creditmanagementbackend.app.service.TbLoanService;
import com.project.creditmanagementbackend.app.util.controller.GenericController;
import com.project.creditmanagementbackend.app.util.response.ApiResponse;
import com.project.creditmanagementbackend.app.util.service.GenericService;
import com.project.creditmanagementbackend.domain.dto.TbLoanDto;
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
@RequestMapping("/api/loans")
@RequiredArgsConstructor
@Tag(name = "Créditos")
@SecurityRequirement(name = "bearerAuth")
public class TbLoanController extends GenericController<TbLoanDto, Long> {

    private final TbLoanService loanService;

    @Override
    protected GenericService<TbLoanDto, Long> getService() {
        return loanService;
    }

    @PostMapping("/create")
    @Operation(summary = "Crear crédito y generar cronograma")
    public ResponseEntity<ApiResponse<TbLoanDto>> create(
            @Valid @RequestBody TbLoanDto dto, HttpServletRequest request) {
        TbLoanDto data = loanService.createLoan(dto);
        return ResponseEntity.status(201)
                .body(ApiResponse.created(data, "Crédito creado", request.getRequestURI()));
    }

    @GetMapping("/{id}/full")
    @Operation(summary = "Obtener crédito con cronograma")
    public ResponseEntity<ApiResponse<TbLoanDto>> getFull(
            @PathVariable Long id, HttpServletRequest request) {
        TbLoanDto data = loanService.getFullLoan(id);
        return ResponseEntity.ok(ApiResponse.success(data, "Crédito obtenido", request.getRequestURI()));
    }

    @GetMapping("/by-borrower/{subjectId}")
    @Operation(summary = "Créditos por prestatario")
    public ResponseEntity<ApiResponse<List<TbLoanDto>>> byBorrower(
            @PathVariable Long subjectId, HttpServletRequest request) {
        List<TbLoanDto> data = loanService.findByBorrowerSubject(subjectId);
        return ResponseEntity.ok(ApiResponse.success(data, "Listado", request.getRequestURI()));
    }

    @PostMapping("/calculate")
    @Operation(summary = "Calcular cuota y totales sin guardar")
    public ResponseEntity<ApiResponse<TbLoanDto>> calculate(
            @Valid @RequestBody TbLoanDto dto, HttpServletRequest request) {
        TbLoanDto data = loanService.calculate(dto);
        return ResponseEntity.ok(ApiResponse.success(data, "Cálculo completado", request.getRequestURI()));
    }
}