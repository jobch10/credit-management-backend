package com.project.creditmanagementbackend.app.service.impl;

import com.project.creditmanagementbackend.app.dao.*;
import com.project.creditmanagementbackend.app.exception.BusinessException;
import com.project.creditmanagementbackend.app.exception.module.GeneralErrors;
import com.project.creditmanagementbackend.app.exception.module.PersonErrors;
import com.project.creditmanagementbackend.app.service.TbFinancialSubjectService;
import com.project.creditmanagementbackend.app.util.service.impl.GenericServiceImpl;
import com.project.creditmanagementbackend.domain.dto.TbFinancialSubjectDto;
import com.project.creditmanagementbackend.domain.entity.*;
import com.project.creditmanagementbackend.domain.enumerado.EnumEmploymentType;
import com.project.creditmanagementbackend.domain.enumerado.EnumSubjectRole;
import com.project.creditmanagementbackend.domain.enumerado.EnumSubjectType;
import com.project.creditmanagementbackend.domain.mapper.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Service
@Transactional
public class TbFinancialSubjectServiceImpl extends GenericServiceImpl<TbFinancialSubjectDto, TbFinancialSubject, Long, TbFinancialSubjectMapper> implements TbFinancialSubjectService {

    private final TbFinancialSubjectDao subjectDao;
    private final TbPersonDao personDao;
    private final TbCreditProfileDao creditProfileDao;
    private final TbSubjectRoleDao subjectRoleDao;
    private final TbFinancialSubjectMapper subjectMapper;
    private final TbPersonMapper personMapper;
    private final TbCreditProfileMapper creditProfileMapper;
    private final TbSubjectRoleMapper subjectRoleMapper;
    private final TbInvestorProfileDao investorProfileDao;
    private final TbInvestorProfileMapper investorProfileMapper;

    public TbFinancialSubjectServiceImpl(
            TbFinancialSubjectDao subjectDao,
            TbPersonDao personDao,
            TbCreditProfileDao creditProfileDao,
            TbSubjectRoleDao subjectRoleDao,
            TbFinancialSubjectMapper subjectMapper,
            TbPersonMapper personMapper,
            TbCreditProfileMapper creditProfileMapper,
            TbSubjectRoleMapper subjectRoleMapper,
            TbInvestorProfileDao investorProfileDao,
            TbInvestorProfileMapper investorProfileMapper) {
        super(subjectDao, subjectMapper);
        this.subjectDao = subjectDao;
        this.personDao = personDao;
        this.creditProfileDao = creditProfileDao;
        this.subjectRoleDao = subjectRoleDao;
        this.subjectMapper = subjectMapper;
        this.personMapper = personMapper;
        this.creditProfileMapper = creditProfileMapper;
        this.subjectRoleMapper = subjectRoleMapper;
        this.investorProfileDao = investorProfileDao;
        this.investorProfileMapper = investorProfileMapper;
    }

    @Override
    public TbFinancialSubjectDto createPersonSubject(TbFinancialSubjectDto dto) {
        if (dto.getPerson() == null) {
            throw new BusinessException(GeneralErrors.DATA_NOT_COMPLETED);
        }
        if (personDao.existsByDni(dto.getPerson().getDni())) {
            throw new BusinessException(PersonErrors.DNI_ALREADY_EXISTS);
        }

        TbFinancialSubject subject = subjectMapper.toEntity(dto);
        subject = subjectDao.save(subject);

        TbPerson person = personMapper.toEntity(dto.getPerson());
        person.setFinancialSubject(subject);
        personDao.save(person);

        if (dto.getRoles() != null) {
            for (var roleDto : dto.getRoles()) {
                var role = subjectRoleMapper.toEntity(roleDto);
                role.setFinancialSubject(subject);
                subjectRoleDao.save(role);
            }
        }

        if (dto.getCreditProfile() != null) {
            TbCreditProfile profile = creditProfileMapper.toEntity(dto.getCreditProfile());
            profile.setFinancialSubject(subject);
            calculateCreditScoring(person, profile);
            creditProfileDao.save(profile);
        }

        if (dto.getInvestorProfile() != null) {
            TbInvestorProfile invProfile = investorProfileMapper.toEntity(dto.getInvestorProfile());
            invProfile.setFinancialSubject(subject);
            investorProfileDao.save(invProfile);
        }

        return subjectMapper.toDto(subjectDao.findById(subject.getId()).orElseThrow());
    }

    @Override
    public TbFinancialSubjectDto updatePersonSubject(Long id, TbFinancialSubjectDto dto) {
        TbFinancialSubject subject = subjectDao.findById(id)
                .orElseThrow(() -> new BusinessException(GeneralErrors.ENTITY_NOT_FOUND));

        subjectMapper.updateEntityFromDto(dto, subject);
        subjectDao.save(subject);

        if (dto.getPerson() != null) {
            TbPerson person = personDao.findByFinancialSubjectId(id)
                    .orElseThrow(() -> new BusinessException(PersonErrors.PERSON_NOT_FOUND));
            personMapper.updateEntityFromDto(dto.getPerson(), person);
            personDao.save(person);
        }

        if (dto.getCreditProfile() != null) {
            TbPerson person = personDao.findByFinancialSubjectId(id).orElse(null);
            TbCreditProfile profile = creditProfileDao.findByFinancialSubjectId(id)
                    .orElseGet(() -> creditProfileMapper.toEntity(dto.getCreditProfile()));
            if (profile.getId() != null) {
                creditProfileMapper.updateEntityFromDto(dto.getCreditProfile(), profile);
            }
            profile.setFinancialSubject(subject);
            calculateCreditScoring(person, profile);
            creditProfileDao.save(profile);
        }

        if (dto.getInvestorProfile() != null) {
            TbInvestorProfile invProfile = investorProfileDao.findByFinancialSubjectId(id)
                    .orElseGet(() -> investorProfileMapper.toEntity(dto.getInvestorProfile()));
            if (invProfile.getId() != null) {
                investorProfileMapper.updateEntityFromDto(dto.getInvestorProfile(), invProfile);
            }
            invProfile.setFinancialSubject(subject);
            investorProfileDao.save(invProfile);
        }

        return subjectMapper.toDto(subjectDao.findById(id).orElseThrow());
    }

    @Override
    @Transactional(readOnly = true)
    public List<TbFinancialSubjectDto> findAllByRole(EnumSubjectRole role) {
        return subjectRoleDao.findByRole(role).stream()
                .map(TbSubjectRole::getFinancialSubject)
                .distinct()
                .map(subjectMapper::toBasicDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public TbFinancialSubjectDto getFullSubject(Long id) {
        TbFinancialSubject subject = subjectDao.findById(id)
                .orElseThrow(() -> new BusinessException(GeneralErrors.ENTITY_NOT_FOUND));
        return subjectMapper.toDto(subject);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TbFinancialSubjectDto> findInvestorPersons() {
        return findByRoleAndType(EnumSubjectRole.INVERSIONISTA, EnumSubjectType.PERSONA);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TbFinancialSubjectDto> findInvestorCompanies() {
        return findByRoleAndType(EnumSubjectRole.INVERSIONISTA, EnumSubjectType.EMPRESA);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TbFinancialSubjectDto> findBorrowerPersons() {
        return findByRoleAndType(EnumSubjectRole.PRESTATARIO, EnumSubjectType.PERSONA);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TbFinancialSubjectDto> findBorrowerCompanies() {
        return findByRoleAndType(EnumSubjectRole.PRESTATARIO, EnumSubjectType.EMPRESA);
    }

    @Override
    public TbFinancialSubjectDto evaluate(TbFinancialSubjectDto dto) {
        if (dto.getPerson() == null || dto.getCreditProfile() == null) {
            throw new BusinessException(GeneralErrors.DATA_NOT_COMPLETED);
        }

        TbPerson person = personMapper.toEntity(dto.getPerson());
        TbCreditProfile profile = creditProfileMapper.toEntity(dto.getCreditProfile());

        calculateCreditScoring(person, profile);

        dto.setCreditProfile(creditProfileMapper.toDto(profile));
        return dto;
    }

    // -------------- METODOS PRIVADOS -----------------
    private List<TbFinancialSubjectDto> findByRoleAndType(EnumSubjectRole role, EnumSubjectType type) {
        return subjectRoleDao
                .findByRoleAndFinancialSubject_SubjectType(role, type)
                .stream()
                .map(TbSubjectRole::getFinancialSubject)
                .distinct()
                .map(subjectMapper::toDto)
                .toList();
    }


    private void calculateCreditScoring(TbPerson person, TbCreditProfile profile) {
        int score = 0;

        // 1. Edad
        if (person != null && person.getBirthDate() != null) {
            int age = Period.between(person.getBirthDate(), LocalDate.now()).getYears();
            if (age < 18) throw new BusinessException(PersonErrors.UNDERAGE_NOT_ALLOWED);
            if (age >= 25 && age <= 50) score += 25;
            else if (age > 50) score += 15;
            else score += 5;
        }

        // 2. Estado civil
        if (person != null && "CASADO".equalsIgnoreCase(person.getMaritalStatus())) score += 15;
        else score += 5;

        // 3. Dependientes
        if (profile.getDependentsCount() != null) {
            if (profile.getDependentsCount() == 0) score += 15;
            else if (profile.getDependentsCount() <= 2) score += 10;
        }

        // 4. Vivienda
        if ("PROPIA".equalsIgnoreCase(profile.getHousingType())) score += 20;
        else if ("FAMILIAR".equalsIgnoreCase(profile.getHousingType())) score += 10;
        else score += 5;

        // 5. Ratio deuda/ingreso
        if (profile.getFixedExpenses() != null
                && profile.getExternalDebtPayments() != null
                && profile.getMonthlyIncome() != null) {
            BigDecimal total = profile.getFixedExpenses().add(profile.getExternalDebtPayments());
            BigDecimal income = profile.getMonthlyIncome();
            if (income.compareTo(BigDecimal.ZERO) > 0) {
                BigDecimal ratio = total.divide(income, 2, RoundingMode.HALF_UP);
                if (ratio.compareTo(new BigDecimal("0.30")) <= 0) score += 25;
                else if (ratio.compareTo(new BigDecimal("0.45")) <= 0) score += 15;
                else score -= 10;
            }
        }

        // 6. Historial interno
        if (profile.getMaxDaysInArrears() != null) {
            if (profile.getMaxDaysInArrears() == 0) score += 15;
            else if (profile.getMaxDaysInArrears() > 5) score -= 30;
        }

        // 7. Situación laboral
        if (profile.getWorkSeniorityMonths() != null) {
            if (profile.getWorkSeniorityMonths() >= 24) score += 15;
            else if (profile.getWorkSeniorityMonths() >= 12) score += 10;
            else if (profile.getWorkSeniorityMonths() >= 6) score += 5;
        }
        if (profile.getEmploymentType() != null) {
            if (profile.getEmploymentType() == EnumEmploymentType.DEPENDIENTE) score += 10;
            else if (profile.getEmploymentType() == EnumEmploymentType.JUBILADO) score += 5;
            else if (profile.getEmploymentType() == EnumEmploymentType.DESEMPLEADO) score -= 30;
        }

        // 8. Infocorp
        if (profile.getInfocorpStatus() != null) {
            switch (profile.getInfocorpStatus()) {
                case NORMAL -> score += 20;
                case CPP -> score -= 20;
                case DUDOSO -> score -= 40;
                case PERDIDA -> score -= 60;
            }
        }

        // 9. Antecedentes
        if (Boolean.TRUE.equals(profile.getHasCriminalRecords())) score -= 50;
        if (Boolean.TRUE.equals(profile.getHasJudicialRecords())) score -= 20;
        if (Boolean.TRUE.equals(profile.getHasPoliceRecords())) score -= 10;

        profile.setCreditScore(score);
        if (score >= 70) profile.setRiskLevel("BAJO");
        else if (score >= 40) profile.setRiskLevel("MEDIO");
        else profile.setRiskLevel("ALTO");
    }
}