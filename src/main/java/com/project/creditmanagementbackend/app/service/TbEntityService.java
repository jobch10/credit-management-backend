package com.project.creditmanagementbackend.app.service;

import com.project.creditmanagementbackend.app.util.service.GenericService;
import com.project.creditmanagementbackend.domain.dto.TbEntityDto;

import com.project.creditmanagementbackend.domain.entity.TbEntity;

public interface TbEntityService extends GenericService<TbEntityDto, Integer> {
    TbEntity findOrCreateEntity(TbEntityDto dto);
}
