package com.project.creditmanagementbackend.domain.mapper;

import com.project.creditmanagementbackend.domain.dto.TbRolePermissionDto;
import com.project.creditmanagementbackend.domain.entity.TbModule;
import com.project.creditmanagementbackend.domain.entity.TbRolePermission;
import com.project.creditmanagementbackend.domain.entity.TbRole;
import com.project.creditmanagementbackend.domain.entity.TbAction;
import com.project.creditmanagementbackend.domain.entity.TbRolePermissionId;
import org.mapstruct.*;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring", uses = { TbModuleMapper.class,
        TbActionMapper.class }, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public abstract class TbRolePermissionMapper implements BaseMapper<TbRolePermissionDto, TbRolePermission> {

    @Mapping(target = "id", expression = "java(entity.getId().getActionId())")
    @Mapping(target = "createdAt", source = "createdAt")
    @Mapping(target = "module", source = "module")
    @Mapping(target = "action", source = "action")
    public abstract TbRolePermissionDto toDto(TbRolePermission entity);

    @Mapping(target = "role", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "module", ignore = true)
    @Mapping(target = "action", ignore = true)
    public abstract TbRolePermission toEntity(TbRolePermissionDto dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    public abstract void updateEntityFromDto(TbRolePermissionDto dto, @MappingTarget TbRolePermission entity);

    public TbRolePermission toEntity(TbRole role, TbModule module, TbAction action, boolean state, LocalDateTime now) {
        TbRolePermission perm = new TbRolePermission();
        perm.setId(new TbRolePermissionId(role.getId(), module.getId(), action.getId()));
        perm.setRole(role);
        perm.setModule(module);
        perm.setAction(action);
        perm.setState(state);
        perm.setCreatedAt(now);
        return perm;
    }
}