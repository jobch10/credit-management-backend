package com.project.creditmanagementbackend.app.controller;

import com.project.creditmanagementbackend.app.service.TbFinancialSubjectService;
import com.project.creditmanagementbackend.app.util.controller.GenericController;
import com.project.creditmanagementbackend.app.util.response.ApiResponse;
import com.project.creditmanagementbackend.app.util.service.GenericService;
import com.project.creditmanagementbackend.domain.dto.TbFinancialSubjectDto;
import com.project.creditmanagementbackend.domain.enumerado.EnumSubjectRole;
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
@RequestMapping("/api/subjects")
@RequiredArgsConstructor
@Tag(name = "Sujetos Financieros", description = "Operaciones de personas y empresas dentro del sistema")
@SecurityRequirement(name = "bearerAuth")
public class TbFinancialSubjectController extends GenericController<TbFinancialSubjectDto, Long> {

    private final TbFinancialSubjectService subjectService;

    @Override
    protected GenericService<TbFinancialSubjectDto, Long> getService() {
        return subjectService;
    }

    @GetMapping("/inversionistas")
    @Operation(summary = "Listar inversionistas")
    public ResponseEntity<ApiResponse<List<TbFinancialSubjectDto>>> listInversionistas(HttpServletRequest request) {
        List<TbFinancialSubjectDto> data = subjectService.findAllByRole(EnumSubjectRole.INVERSIONISTA);
        return ResponseEntity.ok(ApiResponse.success(data, "Listado de inversionistas obtenido", request.getRequestURI()));
    }

    @GetMapping("/prestatarios")
    @Operation(summary = "Listar prestatarios")
    public ResponseEntity<ApiResponse<List<TbFinancialSubjectDto>>> listPrestatarios(HttpServletRequest request) {
        List<TbFinancialSubjectDto> data = subjectService.findAllByRole(EnumSubjectRole.PRESTATARIO);
        return ResponseEntity.ok(ApiResponse.success(data, "Listado de prestatarios obtenido", request.getRequestURI()));
    }

    @PostMapping("/person")
    @Operation(summary = "Crear persona con su subject, roles")
    public ResponseEntity<ApiResponse<TbFinancialSubjectDto>> createPerson(
            @Valid @RequestBody TbFinancialSubjectDto dto, HttpServletRequest request) {
        TbFinancialSubjectDto data = subjectService.createPersonSubject(dto);
        return ResponseEntity.status(201).body(ApiResponse.created(data, "Persona registrada correctamente", request.getRequestURI()));
    }

    @PutMapping("/{id}/person")
    @Operation(summary = "Actualizar persona con su subject")
    public ResponseEntity<ApiResponse<TbFinancialSubjectDto>> updatePerson(
            @PathVariable Long id,
            @Valid @RequestBody TbFinancialSubjectDto dto,
            HttpServletRequest request) {
        TbFinancialSubjectDto data = subjectService.updatePersonSubject(id, dto);
        return ResponseEntity.ok(ApiResponse.success(data, "Persona actualizada correctamente", request.getRequestURI()));
    }

    @GetMapping("/{id}/full")
    @Operation(summary = "Obtener ficha completa del sujeto")
    public ResponseEntity<ApiResponse<TbFinancialSubjectDto>> getFull(
            @PathVariable Long id, HttpServletRequest request) {
        TbFinancialSubjectDto data = subjectService.getFullSubject(id);
        return ResponseEntity.ok(ApiResponse.success(data, "Registro obtenido", request.getRequestURI()));
    }

    @GetMapping("/investors/persons")
    public ResponseEntity<ApiResponse<List<TbFinancialSubjectDto>>> listInvestorPersons(
            HttpServletRequest request) {
        var data = subjectService.findInvestorPersons();
        return ResponseEntity.ok(ApiResponse.success(data, "OK", request.getRequestURI()));
    }

    @GetMapping("/investors/companies")
    public ResponseEntity<ApiResponse<List<TbFinancialSubjectDto>>> listInvestorCompanies(
            HttpServletRequest request) {
        var data = subjectService.findInvestorCompanies();
        return ResponseEntity.ok(ApiResponse.success(data, "OK", request.getRequestURI()));
    }

    @GetMapping("/borrowers/persons")
    public ResponseEntity<ApiResponse<List<TbFinancialSubjectDto>>> listBorrowerPersons(
            HttpServletRequest request) {
        var data = subjectService.findBorrowerPersons();
        return ResponseEntity.ok(ApiResponse.success(data, "OK", request.getRequestURI()));
    }

    @GetMapping("/borrowers/companies")
    public ResponseEntity<ApiResponse<List<TbFinancialSubjectDto>>> listBorrowerCompanies(
            HttpServletRequest request) {
        var data = subjectService.findBorrowerCompanies();
        return ResponseEntity.ok(ApiResponse.success(data, "OK", request.getRequestURI()));
    }

    @PostMapping("/evaluate")
    @Operation(summary = "Evalúa sin guardar")
    public ResponseEntity<ApiResponse<TbFinancialSubjectDto>> evaluate(@Valid @RequestBody TbFinancialSubjectDto dto, HttpServletRequest request) {
        TbFinancialSubjectDto data = subjectService.evaluate(dto);
        return ResponseEntity.ok(ApiResponse.success(data, "Evaluación completada", request.getRequestURI()));
    }
}