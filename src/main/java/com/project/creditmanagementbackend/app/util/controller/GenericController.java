package com.project.creditmanagementbackend.app.util.controller;

import com.project.creditmanagementbackend.app.util.response.ApiResponse;
import com.project.creditmanagementbackend.app.util.service.GenericService;
import com.project.creditmanagementbackend.app.util.pagination.model.PageRequestDto;
import com.project.creditmanagementbackend.app.util.pagination.model.PageResponse;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Validated
public abstract class GenericController<D, K extends Number> {

    protected abstract GenericService<D, K> getService();

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<D>>> findAll(HttpServletRequest request) {
        List<D> data = getService().findAll();
        return ResponseEntity.ok(ApiResponse.success(data, "Listado completo obtenido correctamente", request.getRequestURI()));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<D>>> findAllPaged(@ModelAttribute PageRequestDto pageRequest, @RequestParam Map<String, Object> allParams, HttpServletRequest request) {
        allParams.remove("page");
        allParams.remove("perPage");
        allParams.remove("sort");
        allParams.remove("direction");
        PageResponse<D> data = getService().findAll(pageRequest, allParams);
        return ResponseEntity.ok(ApiResponse.success(data, "Listado paginado obtenido correctamente", request.getRequestURI()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<D>> findById(@PathVariable("id") @Positive K id, HttpServletRequest request) {
        D data = getService().findById(id);
        if (data == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ApiResponse.success(data, "Registro obtenido correctamente", request.getRequestURI()));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<D>> insert(@Valid @RequestBody D dto, HttpServletRequest request) {
        D data = getService().insert(dto);
        return ResponseEntity.status(201).body(ApiResponse.created(data, "Registro creado correctamente", request.getRequestURI()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<D>> update(@PathVariable("id") @Positive K id, @Valid @RequestBody D dto, HttpServletRequest request) {
        D data = getService().update(id, dto);
        if (data == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ApiResponse.success(data, "Registro actualizado correctamente", request.getRequestURI()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable("id") @Positive K id, HttpServletRequest request) {
        getService().delete(id);
        return ResponseEntity.ok(ApiResponse.success(null, "Registro eliminado correctamente", request.getRequestURI()));
    }
}