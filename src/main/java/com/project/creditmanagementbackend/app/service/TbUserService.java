package com.project.creditmanagementbackend.app.service;

import com.project.creditmanagementbackend.app.util.service.GenericService;
import com.project.creditmanagementbackend.domain.dto.request.RegisterRequestDto;
import com.project.creditmanagementbackend.domain.dto.TbUsuarioDto;

public interface TbUserService extends GenericService<TbUsuarioDto, Integer> {
    TbUsuarioDto register(RegisterRequestDto request);
    TbUsuarioDto findByUsernameWithRolesAndModules(String username);
}