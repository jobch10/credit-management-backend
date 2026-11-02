package com.project.creditmanagementbackend.app.controller;

import com.project.creditmanagementbackend.app.service.TbUserService;
import com.project.creditmanagementbackend.app.util.controller.GenericController;
import com.project.creditmanagementbackend.app.util.service.GenericService;
import com.project.creditmanagementbackend.domain.dto.TbUsuarioDto;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
@Tag(name = "Usuarios", description = "Gestión de cuentas de usuario, asignación de roles y visualización de perfiles.")
@SecurityRequirement(name = "bearerAuth")
public class TbUserController extends GenericController<TbUsuarioDto, Integer> {

    private final TbUserService usuarioService;

    @Override
    protected GenericService<TbUsuarioDto, Integer> getService() {
        return usuarioService;
    }
}