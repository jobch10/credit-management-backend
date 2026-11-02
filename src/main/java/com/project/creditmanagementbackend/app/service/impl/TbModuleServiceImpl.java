package com.project.creditmanagementbackend.app.service.impl;

import com.project.creditmanagementbackend.app.dao.TbActionDao;
import com.project.creditmanagementbackend.app.dao.TbModuleActionDao;
import com.project.creditmanagementbackend.app.dao.TbModuleDao;
import com.project.creditmanagementbackend.app.dao.TbRolePermissionDao;
import com.project.creditmanagementbackend.app.exception.BusinessException;
import com.project.creditmanagementbackend.app.exception.module.ActionErrors;
import com.project.creditmanagementbackend.app.exception.module.ModuleErrors;
import com.project.creditmanagementbackend.app.service.TbModuleService;
import com.project.creditmanagementbackend.app.util.service.impl.GenericServiceImpl;
import com.project.creditmanagementbackend.domain.dto.TbModuleDto;
import com.project.creditmanagementbackend.domain.dto.response.ModuleDependenciesResponseDto;
import com.project.creditmanagementbackend.domain.entity.TbAction;
import com.project.creditmanagementbackend.domain.entity.TbModule;
import com.project.creditmanagementbackend.domain.entity.TbModuleAction;
import com.project.creditmanagementbackend.domain.enumerado.ModuleType;
import com.project.creditmanagementbackend.domain.mapper.TbModuleActionMapper;
import com.project.creditmanagementbackend.domain.mapper.TbModuleMapper;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Transactional
public class TbModuleServiceImpl extends GenericServiceImpl<TbModuleDto, TbModule, Integer, TbModuleMapper> implements TbModuleService {

    private final TbModuleDao moduloDao;
    private final TbModuleActionDao moduloAccionDao;
    private final TbActionDao actionDao;
    private final TbModuleActionMapper moduloAccionMapper;
    private final TbRolePermissionDao rolePermissionDao;

    public TbModuleServiceImpl(
            TbModuleDao moduloDao,
            TbModuleActionDao moduloAccionDao,
            TbActionDao actionDao,
            TbRolePermissionDao rolePermissionDao,
            TbModuleMapper mapper,
            TbModuleActionMapper moduloAccionMapper) {
        super(moduloDao, mapper);
        this.moduloDao = moduloDao;
        this.moduloAccionDao = moduloAccionDao;
        this.actionDao = actionDao;
        this.rolePermissionDao = rolePermissionDao;
        this.moduloAccionMapper = moduloAccionMapper;
    }

    @Override
    public TbModuleDto insert(TbModuleDto dto) {
        if (moduloDao.existsByCode(dto.getCode())) {
            throw new BusinessException(ModuleErrors.MODULO_CODE_ALREADY_EXISTS);
        }

        validateHierarchy(null, dto.getParentId());
        validateModuleType(dto);

        TbModule entity = mapper.toEntity(dto);
        entity.setParent(resolveParent(dto.getParentId()));
        entity.setState(true);
        entity.setCreatedAt(LocalDateTime.now());

        return mapper.toDto(moduloDao.save(entity));
    }

    @Override
    public TbModuleDto update(Integer id, TbModuleDto dto) {

        TbModule entity = moduloDao.findById(id)
                .orElseThrow(() -> new BusinessException(ModuleErrors.MODULO_NOT_FOUND));

        if (dto.getCode() != null &&
                !dto.getCode().equalsIgnoreCase(entity.getCode()) &&
                moduloDao.existsByCodeAndIdNot(dto.getCode(), id)) {
            throw new BusinessException(ModuleErrors.MODULO_CODE_ALREADY_EXISTS);
        }

        validateHierarchy(id, dto.getParentId());
        validateModuleType(dto);

        mapper.updateEntityFromDto(dto, entity);
        entity.setParent(resolveParent(dto.getParentId()));

        return mapper.toDto(moduloDao.save(entity));
    }

    @Override
    public List<TbModuleDto> getMenuTree() {
        List<TbModule> modules = moduloDao.findAll()
                .stream()
                .sorted(Comparator.comparing(TbModule::getOrderNum, Comparator.nullsLast(Integer::compareTo)))
                .toList();

        Map<Integer, TbModuleDto> map = modules.stream()
                .map(mapper::toDto)
                .collect(Collectors.toMap(TbModuleDto::getId, Function.identity()));

        modules.stream()
                .filter(m -> m.getParent() != null)
                .forEach(m -> {
                    TbModuleDto parent = map.get(m.getParent().getId());
                    TbModuleDto child = map.get(m.getId());
                    parent.getChildren().add(child);
                });

        map.values().stream()
                .filter(m -> m.getParentId() == null)
                .forEach(this::sortChildrenRecursively);

        return map.values().stream()
                .filter(m -> m.getParentId() == null)
                .sorted(Comparator.comparing(TbModuleDto::getOrderNum, Comparator.nullsLast(Integer::compareTo)))
                .toList();
    }

    @Override
    public List<Long> getActionIdsByModule(Integer moduleId) {
        if (!moduloDao.existsById(moduleId)) {
            throw new BusinessException(ModuleErrors.MODULO_NOT_FOUND);
        }
        return moduloAccionDao.findActionIdsByModuleId(moduleId);
    }

    @Override
    @Transactional
    public TbModuleDto updateModuleActions(Integer moduleId, Set<Integer> actionIds) {
        //Validaciones
        TbModule module = moduloDao.findById(moduleId).orElseThrow(() -> new BusinessException(ModuleErrors.MODULO_NOT_FOUND));
        List<TbAction> actions = actionIds.isEmpty() ? List.of() : actionDao.findAllById(actionIds);
        if (actions.size() != actionIds.size()) throw new BusinessException(ActionErrors.ACCION_NOT_FOUND);

        // Eliminar todas las relaciones actuales
        moduloAccionDao.findActionIdsByModuleId(moduleId);
        moduloAccionDao.flush();

        // Crear nuevas relaciones
        List<TbModuleAction> newAssociations = actions.stream()
                .map(action -> moduloAccionMapper.toEntity(module, action, LocalDateTime.now()))
                .toList();

        moduloAccionDao.saveAll(newAssociations);

        // Estructura dto
        module.setActions(newAssociations);
        return mapper.toDto(module);
    }

    // # <========= Lista completa solo con acciones =========>
    @Override
    @Transactional(readOnly = true)
    public List<TbModuleDto> getModulesWithActions() {
        List<TbModule> modules = moduloDao.findAll();
        return modules.stream()
                .map(mapper::toDtoWithActions)
                .toList();
    }

    // # <========= Eliminar con validaciones =========>
    @Override
    public void delete(Integer id) {
        TbModule entity = moduloDao.findById(id).orElseThrow(() -> new BusinessException(ModuleErrors.MODULO_NOT_FOUND));
        if (entity.getChildren() != null && !entity.getChildren().isEmpty()) {
            throw new BusinessException(ModuleErrors.MODULO_HAS_CHILDREN);
        }
        if (rolePermissionDao.countByModuleId(id) > 0) {
            throw new BusinessException(ModuleErrors.MODULO_HAS_ROLE_PERMISSIONS);
        }
        moduloAccionDao.findActionIdsByModuleId(id);
        moduloAccionDao.flush();
        moduloDao.delete(entity);
    }

    // # <========= Modulos filtrado por type, GRUPO - MODULO =========>
    @Override
    @Transactional(readOnly = true)
    public List<TbModuleDto> getModulesByType(ModuleType type) {
        List<TbModule> modules = (type != null) ? moduloDao.findByType(type) : moduloDao.findAll();
        return modules.stream().map(mapper::toBasicDto).toList();
    }

    // # <========= Verifica las dependencias que tiene asociado el modulo =========>
    @Override
    public ModuleDependenciesResponseDto checkDependencies(Integer id) {

        moduloDao.findById(id).orElseThrow(() -> new BusinessException(ModuleErrors.MODULO_NOT_FOUND));

        long rolePermissionsCount = rolePermissionDao.countByModuleId(id);
        long moduleActionsCount = moduloAccionDao.countByModuloId(id);
        long childrenCount = moduloDao.countByParentId(id);

        boolean hasDependencies = rolePermissionsCount > 0 || childrenCount > 0;

        return new ModuleDependenciesResponseDto(
                hasDependencies,
                rolePermissionsCount,
                moduleActionsCount,
                childrenCount
        );
    }


    /*----------METODOS AUXILIARES-----------*/
    private void sortChildrenRecursively(TbModuleDto parent) {
        if (parent.getChildren() == null || parent.getChildren().isEmpty()) return;

        parent.getChildren().sort(Comparator.comparing(TbModuleDto::getOrderNum, Comparator.nullsLast(Integer::compareTo)));

        parent.getChildren().forEach(this::sortChildrenRecursively);
    }

    private TbModule resolveParent(Integer parentId) {
        if (parentId == null) return null;

        return moduloDao.findById(parentId)
                .orElseThrow(() -> new BusinessException(ModuleErrors.MODULO_PARENT_NOT_FOUND));
    }

    private void validateModuleType(TbModuleDto dto) {

        if (dto.getType() == ModuleType.GRUPO &&
                dto.getRoute() != null &&
                !dto.getRoute().isBlank()) {
            throw new BusinessException(ModuleErrors.GROUP_CANNOT_HAVE_ROUTE);
        }

        if (dto.getType() == ModuleType.MODULO &&
                (dto.getRoute() == null || dto.getRoute().isBlank())) {
            throw new BusinessException(ModuleErrors.MODULE_ROUTE_REQUIRED);
        }
    }

    private void validateHierarchy(Integer id, Integer parentId) {

        if (parentId == null) return;

        if (id != null && parentId.equals(id)) {
            throw new BusinessException(ModuleErrors.MODULO_CANNOT_BE_OWN_PARENT);
        }

        TbModule parent = moduloDao.findById(parentId)
                .orElseThrow(() -> new BusinessException(ModuleErrors.MODULO_PARENT_NOT_FOUND));

        if (!parent.isState()) {
            throw new BusinessException(ModuleErrors.MODULO_PARENT_INACTIVE);
        }

        TbModule current = parent;

        while (current.getParent() != null) {
            if (id != null && current.getParent().getId().equals(id)) {
                throw new BusinessException(ModuleErrors.MODULO_HIERARCHY_CYCLE);
            }
            current = current.getParent();
        }
    }
}