package com.project.creditmanagementbackend.app.service;

import com.project.creditmanagementbackend.app.util.service.GenericService;
import com.project.creditmanagementbackend.domain.dto.TbFinancialSubjectDto;
import com.project.creditmanagementbackend.domain.enumerado.EnumSubjectRole;

import java.util.List;

public interface TbFinancialSubjectService extends GenericService<TbFinancialSubjectDto, Long> {

    /** Crea un sujeto tipo persona con su perfil de crédito y roles. */
    TbFinancialSubjectDto createPersonSubject(TbFinancialSubjectDto dto);

    /** Actualiza el sujeto, los datos de la persona y su perfil de crédito. */
    TbFinancialSubjectDto updatePersonSubject(Long id, TbFinancialSubjectDto dto);

    /** Lista sujetos filtrando por rol (PRESTATARIO, INVERSIONISTA, GARANTE). */
    List<TbFinancialSubjectDto> findAllByRole(EnumSubjectRole role);

    /** Devuelve la ficha completa del sujeto con persona, roles y perfil. */
    TbFinancialSubjectDto getFullSubject(Long id);

    /** Listado de actores financieros */
    List<TbFinancialSubjectDto> findInvestorPersons();

    List<TbFinancialSubjectDto> findInvestorCompanies();

    List<TbFinancialSubjectDto> findBorrowerPersons();

    List<TbFinancialSubjectDto> findBorrowerCompanies();

    /** Evaluar perfil de crédito de un sujeto financiero */
    TbFinancialSubjectDto evaluate(TbFinancialSubjectDto dto);
}