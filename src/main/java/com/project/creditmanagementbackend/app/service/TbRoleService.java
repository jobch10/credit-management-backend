package com.project.creditmanagementbackend.app.service;

import com.project.creditmanagementbackend.app.util.service.GenericService;
import com.project.creditmanagementbackend.domain.dto.TbRoleDto;
import com.project.creditmanagementbackend.domain.dto.request.PermisoRequestDto;

import java.util.List;

public interface TbRoleService extends GenericService<TbRoleDto, Integer> {

    TbRoleDto updateRoleModules(Integer rolId, List<PermisoRequestDto> asignaciones);

    TbRoleDto getRolePermissions(Integer rolId);
}
