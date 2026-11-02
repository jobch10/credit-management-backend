package com.project.creditmanagementbackend.app.service;

import com.project.creditmanagementbackend.domain.dto.TbModuleDto;
import com.project.creditmanagementbackend.domain.dto.request.PermisoRequestDto;

import java.util.List;

public interface TbModuleActionService {
    List<TbModuleDto> updateModuleActions(List<PermisoRequestDto> asignaciones);

    List<TbModuleDto> updateModuleActions(Integer moduleId, List<Integer> actionIds);
}
