package com.project.creditmanagementbackend.app.service;

import com.project.creditmanagementbackend.app.util.service.GenericService;
import com.project.creditmanagementbackend.domain.dto.TbPersonDto;

public interface TbPersonService extends GenericService<TbPersonDto, Long> {

    // Busca una persona directamente en RENIEC/Decolecta
    TbPersonDto findInReniec(String dni);
}