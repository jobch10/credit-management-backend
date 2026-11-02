package com.project.creditmanagementbackend.app.service.impl;

import com.project.creditmanagementbackend.app.dao.TbRoleDao;
import com.project.creditmanagementbackend.app.dao.TbRolePermissionDao;
import com.project.creditmanagementbackend.app.dao.TbModuleDao;
import com.project.creditmanagementbackend.app.dao.TbActionDao;
import com.project.creditmanagementbackend.app.exception.BusinessException;
import com.project.creditmanagementbackend.app.exception.module.*;
import com.project.creditmanagementbackend.app.service.TbRoleService;
import com.project.creditmanagementbackend.app.util.pagination.model.PageRequestDto;
import com.project.creditmanagementbackend.app.util.pagination.model.PageResponse;
import com.project.creditmanagementbackend.app.util.pagination.model.Operator;
import com.project.creditmanagementbackend.app.util.pagination.util.PageableFactory;
import com.project.creditmanagementbackend.app.util.pagination.util.SpecificationBuilder;
import com.project.creditmanagementbackend.app.util.service.impl.GenericServiceImpl;
import com.project.creditmanagementbackend.domain.dto.TbRoleDto;
import com.project.creditmanagementbackend.domain.dto.request.PermisoRequestDto;
import com.project.creditmanagementbackend.domain.entity.TbModule;
import com.project.creditmanagementbackend.domain.entity.TbRole;
import com.project.creditmanagementbackend.domain.entity.TbRolePermission;
import com.project.creditmanagementbackend.domain.entity.TbAction;
import com.project.creditmanagementbackend.domain.mapper.TbRoleMapper;
import com.project.creditmanagementbackend.domain.mapper.TbRolePermissionMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.servlet.http.HttpServletRequest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
public class TbRoleServiceImpl extends GenericServiceImpl<TbRoleDto, TbRole, Integer, TbRoleMapper> implements TbRoleService {

        private final TbRoleDao rolDao;
        private final TbRolePermissionDao rolPermisoDao;
        private final TbModuleDao moduloDao;
        private final TbActionDao accionDao;
        private final TbRolePermissionMapper rolPermisoMapper;

        public TbRoleServiceImpl(
                        TbRoleDao rolDao,
                        TbRoleMapper rolMapper,
                        TbRolePermissionDao rolPermisoDao,
                        TbModuleDao moduloDao,
                        TbActionDao accionDao,
                        TbRolePermissionMapper rolPermisoMapper) {

                super(rolDao, rolMapper);
                this.rolDao = rolDao;
                this.rolPermisoDao = rolPermisoDao;
                this.moduloDao = moduloDao;
                this.accionDao = accionDao;
                this.rolPermisoMapper = rolPermisoMapper;
        }

        @Override
        protected Map<String, Operator> getAllowedFilters() {
                return Map.of(
                                "name", Operator.LIKE,
                                "code", Operator.LIKE,
                                "state", Operator.EQ);
        }

        @Override
        @Transactional(readOnly = true)
        public PageResponse<TbRoleDto> findAll(PageRequestDto request, Map<String, Object> filters, HttpServletRequest httpRequest) {
                Specification<TbRole> spec = SpecificationBuilder.build(filters, getAllowedFilters());
                Pageable pageable = PageableFactory.create(request);
                Page<TbRole> page = rolDao.findAll(spec, pageable);
                Page<TbRoleDto> pageDto = page.map(mapper::toDto);
                return PageResponse.from(pageDto, httpRequest);
        }

        @Override
        @Transactional
        public TbRoleDto insert(TbRoleDto dto) {
                if (rolDao.existsByCode(dto.getCode())) throw new BusinessException(RoleErrors.ROLE_ALREADY_CODE_EXISTS);
                TbRole entity = mapper.toEntity(dto);
                entity.setCreatedAt(LocalDateTime.now());
                return mapper.toDto(rolDao.save(entity));
        }

        @Override
        @Transactional
        public TbRoleDto update(Integer id, TbRoleDto dto) {
                TbRole entity = rolDao.findById(id).orElseThrow(() -> new BusinessException(RoleErrors.ROLE_NOT_FOUND));

                if (dto.getCode() != null && !dto.getCode().equalsIgnoreCase(entity.getCode()) && rolDao.existsByCode(dto.getCode())) {
                        throw new BusinessException(RoleErrors.ROLE_ALREADY_CODE_EXISTS);
                }

                mapper.updateEntityFromDto(dto, entity);
                return mapper.toDto(rolDao.save(entity));
        }

        @Override
        @Transactional
        public void delete(Integer id) {
                if (!rolDao.existsById(id)) throw new BusinessException(RoleErrors.ROLE_NOT_FOUND);
                rolDao.deleteById(id);
        }

        @Override
        @Transactional
        public TbRoleDto findById(Integer id) {
                TbRole entity = rolDao.findById(id)
                                .orElseThrow(() -> new BusinessException(RoleErrors.ROLE_NOT_FOUND));
                return mapper.toDto(entity);
        }

        @Override
        @Transactional
        public TbRoleDto updateRoleModules(Integer roleId, List<PermisoRequestDto> requests) {

            TbRole role = rolDao.findById(roleId).orElseThrow(() -> new BusinessException(RoleErrors.ROLE_NOT_FOUND));

            // Eliminar TODOS los permisos del rol
            rolPermisoDao.deleteAllByRoleId(roleId);

            // 2. Insertar los nuevos
            List<TbRolePermission> newPermissions = Optional.ofNullable(requests)
                    .orElse(List.of())
                    .stream()
                    .filter(req -> req.getModuloId() != null)
                    .flatMap(req -> req.getAccionId().stream().map(actionId -> {

                        TbModule module = moduloDao.findById(req.getModuloId())
                                .orElseThrow(() -> new BusinessException(ModuleErrors.MODULO_NOT_FOUND));

                        // Validación: NO permitir GRUPOS
                        if (module.getType() != null && module.getType().name().equalsIgnoreCase("GRUPO")) {
                            throw new BusinessException(ModuleErrors.MODULO_NOT_FOUND);
                        }

                        TbAction action = accionDao.findById(actionId)
                                .orElseThrow(() -> new BusinessException(ActionErrors.ACCION_NOT_FOUND));

                        return rolPermisoMapper.toEntity(role, module, action, true, LocalDateTime.now());
                    }))
                    .toList();

            rolPermisoDao.saveAll(newPermissions);
            rolPermisoDao.flush();

            // Retornar actualizado
            TbRole updatedRole = rolDao.findById(roleId)
                    .orElseThrow(() -> new BusinessException(RoleErrors.ROLE_NOT_FOUND));

            return mapper.toDto(updatedRole);
        }

        @Override
        @Transactional(readOnly = true)
        public TbRoleDto getRolePermissions(Integer rolId) {
            TbRole role = rolDao.findById(rolId).orElseThrow(() -> new BusinessException(RoleErrors.ROLE_NOT_FOUND));
            return mapper.toDto(role);
        }
}