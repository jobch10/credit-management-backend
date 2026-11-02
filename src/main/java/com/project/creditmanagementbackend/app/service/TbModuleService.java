package com.project.creditmanagementbackend.app.service;

import com.project.creditmanagementbackend.app.util.service.GenericService;
import com.project.creditmanagementbackend.domain.dto.TbModuleDto;
import com.project.creditmanagementbackend.domain.dto.response.ModuleDependenciesResponseDto;
import com.project.creditmanagementbackend.domain.enumerado.ModuleType;

import java.util.List;
import java.util.Set;

public interface TbModuleService extends GenericService<TbModuleDto, Integer> {

    List<TbModuleDto> getMenuTree();

    TbModuleDto updateModuleActions(Integer moduleId, Set<Integer> actionIds);

    List<Long> getActionIdsByModule(Integer moduleId);

    List<TbModuleDto> getModulesByType(ModuleType type);

    List<TbModuleDto> getModulesWithActions();

    ModuleDependenciesResponseDto checkDependencies(Integer id);
}
