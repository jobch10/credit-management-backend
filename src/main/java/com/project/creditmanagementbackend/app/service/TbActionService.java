package com.project.creditmanagementbackend.app.service;

import com.project.creditmanagementbackend.app.util.service.GenericService;
import com.project.creditmanagementbackend.domain.dto.TbActionDto;
import com.project.creditmanagementbackend.domain.dto.response.ActionDependenciesResponseDto;

public interface TbActionService extends GenericService<TbActionDto, Integer> {
    ActionDependenciesResponseDto checkDependencies(Integer id);
}
