package com.project.creditmanagementbackend.app.controller;

import com.project.creditmanagementbackend.app.service.TbPersonService;
import com.project.creditmanagementbackend.app.util.controller.GenericController;
import com.project.creditmanagementbackend.app.util.response.ApiResponse;
import com.project.creditmanagementbackend.app.util.service.GenericService;
import com.project.creditmanagementbackend.domain.dto.TbPersonDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/persons")
@RequiredArgsConstructor
@Tag(name = "Personas", description = "Operaciones relacionadas con las personas naturales del sistema")
@SecurityRequirement(name = "bearerAuth")
public class TbPersonController extends GenericController<TbPersonDto, Long> {

    private final TbPersonService tbPersonService;

    @Override
    protected GenericService<TbPersonDto, Long> getService() {
        return tbPersonService;
    }

    @GetMapping("/reniec/{dni}")
    @Operation(summary = "Consultar DNI en RENIEC")
    public ResponseEntity<ApiResponse<TbPersonDto>> findInReniec(@PathVariable String dni, HttpServletRequest request) {
        return ResponseEntity.ok(ApiResponse.success(tbPersonService.findInReniec(dni), "Consulta de DNI en RENIEC realizada correctamente", request.getRequestURI()));
    }
}
