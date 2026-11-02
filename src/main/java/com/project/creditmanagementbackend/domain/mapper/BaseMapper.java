package com.project.creditmanagementbackend.domain.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

/**
 * Base Mapper interface for common MapStruct operations.
 * 
 * @param <D> DTO Type
 * @param <E> Entity Type
 */
public interface BaseMapper<D, E> {

    D toDto(E entity);

    E toEntity(D dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(D dto, @MappingTarget E entity);
}
