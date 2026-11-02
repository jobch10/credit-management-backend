package com.project.creditmanagementbackend.app.controller;

import com.project.creditmanagementbackend.app.service.TbClientService;
import com.project.creditmanagementbackend.app.util.controller.GenericController;
import com.project.creditmanagementbackend.app.util.service.GenericService;
import com.project.creditmanagementbackend.domain.dto.TbClientDto;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
@Tag(name = "Clients", description = "Operaciones relacionadas con los clientes")
@SecurityRequirement(name = "bearerAuth")
public class TbClientController extends GenericController<TbClientDto, Integer> {

    private final TbClientService clientService;

    @Override
    protected GenericService<TbClientDto, Integer> getService() {
        return clientService;
    }
}
