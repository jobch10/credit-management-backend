package com.project.creditmanagementbackend.app.util.service.impl;

import com.project.creditmanagementbackend.app.exception.BusinessException;
import com.project.creditmanagementbackend.app.exception.module.GeneralErrors;
import com.project.creditmanagementbackend.app.util.service.GenericService;
import com.project.creditmanagementbackend.app.util.pagination.model.Operator;
import com.project.creditmanagementbackend.app.util.pagination.model.PageRequestDto;
import com.project.creditmanagementbackend.app.util.pagination.model.PageResponse;
import com.project.creditmanagementbackend.app.util.pagination.util.PageableFactory;
import com.project.creditmanagementbackend.app.util.pagination.util.SpecificationBuilder;
import com.project.creditmanagementbackend.domain.mapper.BaseMapper;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

public abstract class GenericServiceImpl<D, E, K, M extends BaseMapper<D, E>>
        implements GenericService<D, K> {

    protected final JpaRepository<E, K> repository;
    protected final M mapper;

    protected GenericServiceImpl(JpaRepository<E, K> repository, M mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    protected D toDto(E entity) {
        return mapper.toDto(entity);
    }

    protected E toEntity(D dto) {
        return mapper.toEntity(dto);
    }

    protected void updateEntity(E entity, D dto) {
        mapper.updateEntityFromDto(dto, entity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<D> findAll() {
        return repository.findAll().stream().map(this::toDto).toList();
    }

    @Transactional(readOnly = true)
    public PageResponse<D> findAll(PageRequestDto request, Map<String, Object> filters,
            HttpServletRequest httpRequest) {

        if (!(repository instanceof JpaSpecificationExecutor<?> executor)) {
            throw new BusinessException(GeneralErrors.REPOSITORY_NOT_SPECIFICATION);
        }

        @SuppressWarnings("unchecked")
        JpaSpecificationExecutor<E> specExecutor = (JpaSpecificationExecutor<E>) executor;

        Specification<E> spec = SpecificationBuilder.build(filters, getAllowedFilters());

        Pageable pageable = PageableFactory.create(request);

        Page<E> page = specExecutor.findAll(spec, pageable);

        Page<D> pageDto = page.map(this::toDto);

        return PageResponse.from(pageDto, httpRequest);
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<D> findAll(PageRequestDto request, Map<String, Object> filters) {
        return findAll(request, filters, null);
    }

    @Override
    @Transactional(readOnly = true)
    public D findById(K id) {
        E entity = repository.findById(id)
                .orElseThrow(() -> new BusinessException(GeneralErrors.ENTITY_NOT_FOUND));
        return toDto(entity);
    }

    @Override
    @Transactional
    public D insert(D dto) {
        E entity = toEntity(dto);
        return toDto(repository.save(entity));
    }

    @Override
    @Transactional
    public D update(K id, D dto) {
        E entity = repository.findById(id)
                .orElseThrow(() -> new BusinessException(GeneralErrors.ENTITY_NOT_FOUND));
        updateEntity(entity, dto);
        return toDto(repository.save(entity));
    }

    @Override
    @Transactional
    public void delete(K id) {
        if (!repository.existsById(id)) {
            throw new BusinessException(GeneralErrors.ENTITY_NOT_FOUND);
        }
        repository.deleteById(id);
    }

    protected Map<String, Operator> getAllowedFilters() {
        return Map.of();
    }
}