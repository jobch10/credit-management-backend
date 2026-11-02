package com.project.creditmanagementbackend.app.util.dto;

public interface GenericDto<E, D> {
    D fromEntity(E entity);

    D fromEntity(D dto, E entity);

    E toEntity();

}

