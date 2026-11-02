package com.project.creditmanagementbackend.app.controller;

import com.project.creditmanagementbackend.app.service.TbPaymentService;
import com.project.creditmanagementbackend.app.util.controller.GenericController;
import com.project.creditmanagementbackend.app.util.response.ApiResponse;
import com.project.creditmanagementbackend.app.util.service.GenericService;
import com.project.creditmanagementbackend.domain.dto.TbPaymentDto;
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
@RequestMapping("/api/payments")
@RequiredArgsConstructor
@Tag(name = "Pagos")
@SecurityRequirement(name = "bearerAuth")
public class TbPaymentController extends GenericController<TbPaymentDto, Long> {

    private final TbPaymentService paymentService;

    @Override
    protected GenericService<TbPaymentDto, Long> getService() {
        return paymentService;
    }

    @PostMapping("/register")
    @Operation(summary = "Registrar pago y actualizar cuota")
    public ResponseEntity<ApiResponse<TbPaymentDto>> register(
            @Valid @RequestBody TbPaymentDto dto, HttpServletRequest request) {
        TbPaymentDto data = paymentService.registerPayment(dto);
        return ResponseEntity.status(201)
                .body(ApiResponse.created(data, "Pago registrado", request.getRequestURI()));
    }

    @GetMapping("/by-loan/{loanId}")
    @Operation(summary = "Pagos por crédito")
    public ResponseEntity<ApiResponse<List<TbPaymentDto>>> byLoan(
            @PathVariable Long loanId, HttpServletRequest request) {
        List<TbPaymentDto> data = paymentService.findByLoan(loanId);
        return ResponseEntity.ok(ApiResponse.success(data, "Listado", request.getRequestURI()));
    }
}