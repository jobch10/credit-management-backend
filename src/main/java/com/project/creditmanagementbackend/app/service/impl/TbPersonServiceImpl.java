package com.project.creditmanagementbackend.app.service.impl;

import com.project.creditmanagementbackend.app.client.decolecta.DecolectaClient;
import com.project.creditmanagementbackend.app.client.decolecta.dto.ReniecResponse;
import com.project.creditmanagementbackend.app.dao.TbPersonDao;
import com.project.creditmanagementbackend.app.exception.BusinessException;
import com.project.creditmanagementbackend.app.exception.module.GeneralErrors;
import com.project.creditmanagementbackend.app.exception.module.PersonErrors;
import com.project.creditmanagementbackend.app.service.TbPersonService;
import com.project.creditmanagementbackend.domain.dto.TbPersonDto;
import com.project.creditmanagementbackend.domain.entity.TbPerson;
import com.project.creditmanagementbackend.domain.mapper.TbPersonMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class TbPersonServiceImpl implements TbPersonService {
    private final TbPersonDao personDao;
    private final TbPersonMapper personMapper;
    private final DecolectaClient decolectaClient;

    @Override
    @Transactional
    public TbPersonDto insert(TbPersonDto dto) {
        if (personDao.existsByDni(dto.getDni())) {
            throw new BusinessException(PersonErrors.DNI_ALREADY_EXISTS);
        }
        TbPerson person = personMapper.toEntity(dto);
        return personMapper.toDto(personDao.save(person));
    }

    @Override
    @Transactional
    public TbPersonDto update(Long id, TbPersonDto dto) {
        TbPerson entity = personDao.findById(id)
                .orElseThrow(() -> new BusinessException(PersonErrors.PERSON_NOT_FOUND));

        if (personDao.existsByDniAndIdNot(dto.getDni(), id)) {
            throw new BusinessException(PersonErrors.DNI_ALREADY_EXISTS);
        }

        personMapper.updateEntityFromDto(dto, entity);
        return personMapper.toDto(personDao.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public List<TbPersonDto> findAll() {
        return personDao.findAll().stream()
                .map(personMapper::toDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public TbPersonDto findById(Long id) {
        TbPerson person = personDao.findById(id)
                .orElseThrow(() -> new BusinessException(GeneralErrors.ENTITY_NOT_FOUND));
        return personMapper.toDto(person);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        TbPerson person = personDao.findById(id)
                .orElseThrow(() -> new BusinessException(PersonErrors.PERSON_NOT_FOUND));
        personDao.delete(person);
    }

    @Override
    @Transactional(readOnly = true)
    public TbPersonDto findInReniec(String dni) {
        if (dni == null || !dni.matches("\\d{8}")) {
            throw new BusinessException(PersonErrors.DNI_INCORRECT_FORMAT);
        }

        ReniecResponse response = decolectaClient.consultarDni(dni);

        if (response == null || response.documentNumber() == null) {
            throw new BusinessException(PersonErrors.PERSON_NOT_FOUND);
        }

        return TbPersonDto.builder()
                .dni(response.documentNumber())
                .firstName(response.firstName())
                .lastName(response.firstLastName() + " " + response.secondLastName())
                .build();
    }
}
