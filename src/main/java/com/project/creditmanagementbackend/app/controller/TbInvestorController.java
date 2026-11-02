package com.project.creditmanagementbackend.app.controller;

import com.project.creditmanagementbackend.app.service.TbInvestorService;
import com.project.creditmanagementbackend.app.util.controller.GenericController;
import com.project.creditmanagementbackend.app.util.service.GenericService;
import com.project.creditmanagementbackend.domain.dto.TbInvestorDto;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/investors")
@RequiredArgsConstructor
@Tag(name = "Investors", description = "Operaciones relacionadas con los inversionistas")
@SecurityRequirement(name = "bearerAuth")
public class TbInvestorController extends GenericController<TbInvestorDto, Integer> {

    private final TbInvestorService investorService;

    @Override
    protected GenericService<TbInvestorDto, Integer> getService() {
        return investorService;
    }
}
