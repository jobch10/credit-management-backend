package com.project.creditmanagementbackend.app.service.impl;

import com.project.creditmanagementbackend.app.dao.TbActionDao;
import com.project.creditmanagementbackend.app.dao.TbModuleActionDao;
import com.project.creditmanagementbackend.app.dao.TbRolePermissionDao;
import com.project.creditmanagementbackend.app.exception.BusinessException;
import com.project.creditmanagementbackend.app.exception.module.ActionErrors;
import com.project.creditmanagementbackend.app.service.TbActionService;
import com.project.creditmanagementbackend.app.util.pagination.model.PageRequestDto;
import com.project.creditmanagementbackend.app.util.pagination.model.PageResponse;
import com.project.creditmanagementbackend.app.util.pagination.model.Operator;
import com.project.creditmanagementbackend.app.util.pagination.util.PageableFactory;
import com.project.creditmanagementbackend.app.util.pagination.util.SpecificationBuilder;
import com.project.creditmanagementbackend.app.util.service.impl.GenericServiceImpl;
import com.project.creditmanagementbackend.domain.dto.TbActionDto;
import com.project.creditmanagementbackend.domain.dto.response.ActionDependenciesResponseDto;
import com.project.creditmanagementbackend.domain.entity.TbAction;
import com.project.creditmanagementbackend.domain.mapper.TbActionMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Map;

@Service
@Transactional
public class TbActionServiceImpl extends GenericServiceImpl<TbActionDto, TbAction, Integer, TbActionMapper> implements TbActionService {

    private final TbActionDao accionDao;
    private final TbModuleActionDao moduleActionDao;
    private final TbRolePermissionDao rolePermissionDao;

    public TbActionServiceImpl(TbActionDao accionDao, TbActionMapper accionMapper, TbModuleActionDao moduleActionDao, TbRolePermissionDao rolePermissionDao) {
        super(accionDao, accionMapper);
        this.accionDao = accionDao;
        this.moduleActionDao = moduleActionDao;
        this.rolePermissionDao = rolePermissionDao;
    }

    @Override
    protected Map<String, Operator> getAllowedFilters() {
        return Map.of("name", Operator.LIKE, "code", Operator.LIKE, "state", Operator.EQ);
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<TbActionDto> findAll(PageRequestDto request, Map<String, Object> filters,
            HttpServletRequest httpRequest) {
        Specification<TbAction> spec = SpecificationBuilder.build(filters, getAllowedFilters());
        Pageable pageable = PageableFactory.create(request);
        Page<TbAction> page = accionDao.findAll(spec, pageable);
        Page<TbActionDto> pageDto = page.map(mapper::toDto);
        return PageResponse.from(pageDto, httpRequest);
    }

    @Override
    @Transactional
    public TbActionDto insert(TbActionDto dto) {
        if (accionDao.existsByCode(dto.getCode())) {
            throw new BusinessException(ActionErrors.ACCION_CODE_ALREADY_EXISTS);
        }
        TbAction entity = mapper.toEntity(dto);
        entity.setState(true);
        entity.setCreatedAt(LocalDateTime.now());
        return mapper.toDto(accionDao.save(entity));
    }

    @Override
    @Transactional
    public TbActionDto update(Integer id, TbActionDto dto) {
        TbAction entity = accionDao.findById(id)
                .orElseThrow(() -> new BusinessException(ActionErrors.ACCION_NOT_FOUND));

        if (dto.getCode() != null &&
                !dto.getCode().equalsIgnoreCase(entity.getCode()) &&
                accionDao.existsByCode(dto.getCode())) {
            throw new BusinessException(ActionErrors.ACCION_CODE_ALREADY_EXISTS);
        }

        mapper.updateEntityFromDto(dto, entity);
        return mapper.toDto(accionDao.save(entity));
    }

    // # <========= Obtener acción =========>
    @Override
    @Transactional(readOnly = true)
    public TbActionDto findById(Integer id) {
        TbAction entity = accionDao.findById(id).orElseThrow(() -> new BusinessException(ActionErrors.ACCION_NOT_FOUND));
        return mapper.toDto(entity);
    }

    // # <========= Eliminar acción y regitros dependientes =========>
    @Transactional
    public void deleteComplete(Integer id) {
        TbAction entity = accionDao.findById(id).orElseThrow(() -> new BusinessException(ActionErrors.ACCION_NOT_FOUND));
        moduleActionDao.deleteByAccionId(id);
        rolePermissionDao.deleteByActionId(id);
        moduleActionDao.flush();
        rolePermissionDao.flush();
        accionDao.delete(entity);
    }


    // # <========= Eliminar acción - validación =========>
    @Override
    @Transactional
    public void delete(Integer id) {
        TbAction entity = accionDao.findById(id).orElseThrow(() -> new BusinessException(ActionErrors.ACCION_NOT_FOUND));
        ActionDependenciesResponseDto actionDependenciesResponseDto = checkDependencies(id);
        if (Boolean.TRUE.equals(actionDependenciesResponseDto.isHasDependencies())) {
            throw new BusinessException(ActionErrors.ACCION_HAS_DEPENDENCIES);
        }
        accionDao.delete(entity);
    }

    // # <========= Número de módulos asociados a una acción específica =========>
    @Override
    @Transactional(readOnly = true)
    public ActionDependenciesResponseDto checkDependencies(Integer id) {
        if (!accionDao.existsById(id)) {
            throw new BusinessException(ActionErrors.ACCION_NOT_FOUND);
        }
        long moduleActionsCount = moduleActionDao.countByAccionId(id);
        long rolePermissionsCount = rolePermissionDao.countByActionId(id);
        boolean hasDependencies = moduleActionsCount > 0 || rolePermissionsCount > 0;
        return new ActionDependenciesResponseDto(
                hasDependencies,
                moduleActionsCount,
                rolePermissionsCount
        );
    }

}
