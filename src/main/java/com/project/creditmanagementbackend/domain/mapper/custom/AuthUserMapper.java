package com.project.creditmanagementbackend.domain.mapper.custom;

import com.project.creditmanagementbackend.domain.dto.TbActionDto;
import com.project.creditmanagementbackend.domain.dto.TbModuleDto;
import com.project.creditmanagementbackend.domain.dto.TbRoleDto;
import com.project.creditmanagementbackend.domain.dto.response.AuthUserResponseDto;
import com.project.creditmanagementbackend.domain.entity.TbModule;
import com.project.creditmanagementbackend.domain.entity.TbRolePermission;
import com.project.creditmanagementbackend.domain.entity.TbUser;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.*;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public abstract class AuthUserMapper {

    @Mapping(target = "user", source = "usuario", qualifiedByName = "mapUser")
    @Mapping(target = "role", source = "role", qualifiedByName = "mapRole")
    @Mapping(target = "path", constant = "/api/auth/login")
    public AuthUserResponseDto toAuthUserResponse(
            TbUser usuario,
            TbRoleDto role,
            List<TbRolePermission> modules
    ) {
        AuthUserResponseDto dto = new AuthUserResponseDto();
        dto.setUser(mapUser(usuario));
        dto.setRole(mapRole(role));
        dto.setPath("/api/auth/login");
        dto.setPermissionModules(buildModuleTreeWithModules(modules));
        return dto;
    }

    @Named("mapUser")
    protected AuthUserResponseDto.UserDto mapUser(TbUser usuario) {
        if (usuario == null) return null;
        AuthUserResponseDto.UserDto dto = new AuthUserResponseDto.UserDto();
        dto.setId(usuario.getId());
        dto.setNames(usuario.getNames());
        dto.setEmail(usuario.getEmail());
        dto.setUsername(usuario.getUsername());
        return dto;
    }

    @Named("mapRole")
    protected AuthUserResponseDto.RoleDto mapRole(TbRoleDto roleDto) {
        if (roleDto == null) return null;
        AuthUserResponseDto.RoleDto dto = new AuthUserResponseDto.RoleDto();
        dto.setId(roleDto.getId());
        dto.setCode(roleDto.getCode());
        dto.setName(roleDto.getName());
        return dto;
    }

    private void sortChildrenRecursively(TbModuleDto parent) {
        parent.getChildren().sort(Comparator.comparing(TbModuleDto::getOrderNum, Comparator.nullsLast(Integer::compareTo)));
        parent.getChildren().forEach(this::sortChildrenRecursively);
    }

    protected List<TbModuleDto> buildModuleTreeWithModules(List<TbRolePermission> permissions) {
        if (permissions == null || permissions.isEmpty()) {
            return Collections.emptyList();
        }

        Map<Integer, TbModuleDto> moduleDtoMap = new HashMap<>();

        for (TbRolePermission permission : permissions) {
            TbModule module = permission.getModule();

            addModuleWithParents(module, moduleDtoMap);

            TbModuleDto moduleDto = moduleDtoMap.get(module.getId());

            if (permission.getAction() != null
                    && !"LISTAR".equalsIgnoreCase(permission.getAction().getCode())) {

                boolean alreadyExists = moduleDto.getActions().stream()
                        .anyMatch(a -> Objects.equals(a.getId(), permission.getAction().getId()));

                if (!alreadyExists) {
                    TbActionDto actionDto = new TbActionDto();
                    actionDto.setId(permission.getAction().getId());
                    actionDto.setCode(permission.getAction().getCode());
                    actionDto.setName(permission.getAction().getName());
                    actionDto.setIcon(permission.getAction().getIcon());
                    actionDto.setColor(permission.getAction().getColor());
                    actionDto.setState(permission.getAction().isState());

                    moduleDto.getActions().add(actionDto);
                }
            }
        }

        // Construir árbol
        Map<Integer, TbModuleDto> roots = new HashMap<>(moduleDtoMap);

        moduleDtoMap.values().forEach(m -> {
            if (m.getParentId() != null && moduleDtoMap.containsKey(m.getParentId())) {
                TbModuleDto parent = moduleDtoMap.get(m.getParentId());

                boolean alreadyChild = parent.getChildren().stream()
                        .anyMatch(c -> Objects.equals(c.getId(), m.getId()));

                if (!alreadyChild) {
                    parent.getChildren().add(m);
                }

                roots.remove(m.getId());
            }
        });

        // Ordenar
        roots.values().forEach(this::sortChildrenRecursively);

        // Quitar grupos vacíos
        return roots.values().stream().map(this::pruneEmptyGroups).filter(Objects::nonNull).toList();
    }

    private void addModuleWithParents(TbModule module, Map<Integer, TbModuleDto> moduleDtoMap) {
        if (module == null) return;

        moduleDtoMap.computeIfAbsent(module.getId(), id -> {
            TbModuleDto dto = new TbModuleDto();
            dto.setId(module.getId());
            dto.setCode(module.getCode());
            dto.setName(module.getName());
            dto.setDescription(module.getDescription());
            dto.setIcon(module.getIcon());
            dto.setRoute(module.getRoute());
            dto.setType(module.getType());
            dto.setOrderNum(module.getOrderNum());
            dto.setParentId(module.getParent() != null ? module.getParent().getId() : null);
            dto.setState(module.isState());
            dto.setCreatedAt(module.getCreatedAt());
            dto.setUpdatedAt(module.getUpdatedAt());
            dto.setActions(new ArrayList<>());
            dto.setChildren(new ArrayList<>());
            return dto;
        });

        if (module.getParent() != null) {
            addModuleWithParents(module.getParent(), moduleDtoMap);
        }
    }

    private TbModuleDto pruneEmptyGroups(TbModuleDto module) {
        if (module.getChildren() != null && !module.getChildren().isEmpty()) {
            List<TbModuleDto> cleanedChildren = module.getChildren().stream()
                    .map(this::pruneEmptyGroups)
                    .filter(Objects::nonNull)
                    .toList();

            module.setChildren(new ArrayList<>(cleanedChildren));
        }

        boolean isGroup = module.getType() != null
                && "GRUPO".equalsIgnoreCase(module.getType().name());

        if (isGroup && (module.getChildren() == null || module.getChildren().isEmpty())) {
            return null;
        }

        return module;
    }

}