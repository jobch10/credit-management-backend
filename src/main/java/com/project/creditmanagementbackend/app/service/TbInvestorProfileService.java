package com.project.creditmanagementbackend.app.service;

import com.project.creditmanagementbackend.app.util.service.GenericService;
import com.project.creditmanagementbackend.domain.dto.TbInvestorProfileDto;

public interface TbInvestorProfileService extends GenericService<TbInvestorProfileDto, Long> {

    /** Obtiene el perfil de inversionista del sujeto. */
    TbInvestorProfileDto findByFinancialSubjectId(Long financialSubjectId);

}