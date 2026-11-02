package com.project.creditmanagementbackend.app.util.service;

import com.project.creditmanagementbackend.app.util.pagination.model.PageRequestDto;
import com.project.creditmanagementbackend.app.util.pagination.model.PageResponse;

import java.util.List;
import java.util.Map;

public interface GenericService<D, K> {

    List<D> findAll();

    default PageResponse<D> findAll(PageRequestDto request, Map<String, Object> filters) {
        throw new UnsupportedOperationException("Paginación no implementada");
    }

    D findById(K id);

    D insert(D dto);

    D update(K id, D dto);

    void delete(K id);
}