package com.project.creditmanagementbackend.app.service.impl;

import com.project.creditmanagementbackend.app.dao.TbRoleDao;
import com.project.creditmanagementbackend.app.dao.TbUserDao;
import com.project.creditmanagementbackend.app.exception.BusinessException;
import com.project.creditmanagementbackend.app.exception.module.RoleErrors;
import com.project.creditmanagementbackend.app.exception.module.UserErrors;
import com.project.creditmanagementbackend.app.service.TbUserService;
import com.project.creditmanagementbackend.app.util.pagination.model.Operator;
import com.project.creditmanagementbackend.app.util.pagination.model.PageRequestDto;
import com.project.creditmanagementbackend.app.util.pagination.model.PageResponse;
import com.project.creditmanagementbackend.app.util.pagination.util.PageableFactory;
import com.project.creditmanagementbackend.app.util.pagination.util.SpecificationBuilder;
import com.project.creditmanagementbackend.app.util.service.impl.GenericServiceImpl;
import com.project.creditmanagementbackend.domain.dto.request.RegisterRequestDto;
import com.project.creditmanagementbackend.domain.dto.TbUsuarioDto;
import com.project.creditmanagementbackend.domain.entity.TbRole;
import com.project.creditmanagementbackend.domain.entity.TbUser;
import com.project.creditmanagementbackend.domain.mapper.TbUserMapper;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Map;

@Service
@Transactional
@Primary
public class TbUserServiceImpl extends GenericServiceImpl<TbUsuarioDto, TbUser, Integer, TbUserMapper>
        implements TbUserService {

    private final TbUserDao usuarioDao;
    private final TbRoleDao rolDao;
    private final PasswordEncoder passwordEncoder;

    public TbUserServiceImpl(TbUserDao usuarioDao, TbUserMapper usuarioMapper,
                             TbRoleDao rolDao, PasswordEncoder passwordEncoder) {
        super(usuarioDao, usuarioMapper);
        this.usuarioDao = usuarioDao;
        this.rolDao = rolDao;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public TbUsuarioDto register(RegisterRequestDto request) {

        if (usuarioDao.existsByUsername(request.getUsername())) {
            throw new BusinessException(UserErrors.USER_ALREADY_EXISTS);
        }

        TbUser usuario = mapper.toEntity(request);

        usuario.setPassword(passwordEncoder.encode(request.getPassword()));
        usuario.setState(true);
        usuario.setCreatedAt(LocalDateTime.now());

        TbRole role = rolDao.findById(request.getRoleId())
                .orElseThrow(() -> new BusinessException(RoleErrors.ROLE_NOT_FOUND));

        usuario.setRole(role);
        TbUser usuarioGuardado = usuarioDao.save(usuario);

        return mapper.toDto(usuarioGuardado);
    }

    @Override
    @Transactional(readOnly = true)
    public TbUsuarioDto findByUsernameWithRolesAndModules(String username) {

        TbUser usuario = usuarioDao
                .findByUsername(username)
                .orElseThrow(() -> new BusinessException(UserErrors.USER_NOT_FOUND));

        return mapper.toDto(usuario);
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<TbUsuarioDto> findAll(PageRequestDto request, Map<String, Object> filters,
            jakarta.servlet.http.HttpServletRequest httpRequest) {

        Specification<TbUser> spec = SpecificationBuilder.build(filters, getAllowedFilters());
        Pageable pageable = PageableFactory.create(request);
        Page<TbUser> page = usuarioDao.findAll(spec, pageable);
        Page<TbUsuarioDto> pageDto = page.map(mapper::toDto);

        return PageResponse.from(pageDto, httpRequest);
    }

    @Override
    @Transactional(readOnly = true)
    public TbUsuarioDto findById(Integer id) {
        return mapper.toDto(usuarioDao.findById(id)
                .orElseThrow(() -> new BusinessException(UserErrors.USER_NOT_FOUND)));
    }

    @Override
    @Transactional
    public TbUsuarioDto insert(TbUsuarioDto dto) {

        if (usuarioDao.existsByUsername(dto.getUsername()))
            throw new BusinessException(UserErrors.USER_ALREADY_EXISTS);

        if (usuarioDao.existsByEmail(dto.getEmail()))
            throw new BusinessException(UserErrors.EMAIL_ALREADY_EXISTS);

        TbUser entity = mapper.toEntity(dto);
        TbRole role = null;
        if (dto.getRoleId() != null) {

            if (dto.getRoleId() != null) {
                role = rolDao.findById(dto.getRoleId()).orElse(null);
            }
            if (role == null) {
                throw new BusinessException(RoleErrors.ROLE_NOT_FOUND);
            }
            entity.setRole(role);
        } else {
            throw new BusinessException(RoleErrors.ROLE_NOT_FOUND);
        }

        entity.setPassword(passwordEncoder.encode(dto.getUsername() + "123*"));
        entity.setState(true);
        entity.setRole(role);
        entity.setCreatedAt(LocalDateTime.now());

        return mapper.toDto(usuarioDao.save(entity));
    }

    @Override
    @Transactional
    public TbUsuarioDto update(Integer id, TbUsuarioDto dto) {

        TbUser entity = usuarioDao.findById(id)
                .orElseThrow(() -> new BusinessException(UserErrors.USER_NOT_FOUND));

        if (dto.getUsername() != null &&
                !dto.getUsername().equalsIgnoreCase(entity.getUsername()) &&
                usuarioDao.existsByUsername(dto.getUsername())) {

            throw new BusinessException(UserErrors.USER_ALREADY_EXISTS);
        }
        if (dto.getEmail() != null &&
                !dto.getEmail().equalsIgnoreCase(entity.getEmail()) &&
                usuarioDao.existsByEmail(dto.getEmail()))
            throw new BusinessException(UserErrors.EMAIL_ALREADY_EXISTS);

        if (dto.getRoleId() != null) {
            TbRole role = null;
            if (dto.getRoleId()  != null) {
                role = rolDao.findById(dto.getRoleId()).orElse(null);
            }
            if (role != null) {
                entity.setRole(role);
            } else {
                throw new BusinessException(RoleErrors.ROLE_NOT_FOUND);
            }
        }

        mapper.updateEntityFromDto(dto, entity);
        return mapper.toDto(usuarioDao.save(entity));
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        if (!usuarioDao.existsById(id))
            throw new BusinessException(UserErrors.USER_NOT_FOUND);
        usuarioDao.deleteById(id);
    }

    @Override
    protected Map<String, Operator> getAllowedFilters() {
        return Map.of(
                "username", Operator.LIKE,
                "names", Operator.LIKE,
                "email", Operator.LIKE,
                "state", Operator.EQ,
                "role.code", Operator.LIKE

        );
    }
}