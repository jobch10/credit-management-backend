package com.project.creditmanagementbackend.app.service;

import com.project.creditmanagementbackend.app.util.service.GenericService;
import com.project.creditmanagementbackend.domain.dto.TbCreditProfileDto;

public interface TbCreditProfileService extends GenericService<TbCreditProfileDto, Long> {
    TbCreditProfileDto findByFinancialSubjectId(Long financialSubjectId);
}
