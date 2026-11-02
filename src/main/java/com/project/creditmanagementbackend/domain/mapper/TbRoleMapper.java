package com.project.creditmanagementbackend.domain.mapper;

import com.project.creditmanagementbackend.domain.dto.TbRoleDto;
import com.project.creditmanagementbackend.domain.dto.TbModuleGroupDto;
import com.project.creditmanagementbackend.domain.entity.TbRole;
import com.project.creditmanagementbackend.domain.entity.TbRolePermission;
import com.project.creditmanagementbackend.domain.entity.TbModule;
import com.project.creditmanagementbackend.domain.entity.TbAction;
import org.mapstruct.*;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public abstract class TbRoleMapper implements BaseMapper<TbRoleDto, TbRole> {

    @Override
    @Mapping(target = "modules", expression = "java(mapGroupedModules(entity.getModules()))")
    public abstract TbRoleDto toDto(TbRole entity);

    @Named("toSimpleRol")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "code", source = "code")
    @Mapping(target = "name", source = "name")
    public abstract TbRoleDto toSimpleDto(TbRole entity);

    @Override
    @Mapping(target = "modules", ignore = true)
    public abstract TbRole toEntity(TbRoleDto dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "modules", ignore = true)
    public abstract void updateEntityFromDto(TbRoleDto dto, @MappingTarget TbRole entity);

    protected List<TbModuleGroupDto> mapGroupedModules(List<TbRolePermission> modules) {
        if (modules == null)
            return Collections.emptyList();

        AtomicInteger idCounter = new AtomicInteger(1);

        return modules.stream()
                .filter(TbRolePermission::isState)
                .collect(Collectors.groupingBy(TbRolePermission::getModule))
                .entrySet()
                .stream()
                .map(entry -> {
                    TbModule modulo = entry.getKey();
                    List<TbRolePermission> perms = entry.getValue();

                    TbModuleGroupDto.ModuleGroupDto moduleDto = TbModuleGroupDto.ModuleGroupDto.builder()
                            .id(modulo.getId())
                            .code(modulo.getCode())
                            .name(modulo.getName())
                            .state(modulo.isState())
                            .actions(perms.stream()
                                    .map(p -> {
                                        TbAction accion = p.getAction();
                                        return TbModuleGroupDto.ModuleGroupDto.ActionGroupDto.builder()
                                                .id(accion.getId())
                                                .code(accion.getCode())
                                                .name(accion.getName())
                                                .state(accion.isState())
                                                .build();
                                    })
                                    .toList())
                            .build();

                    return TbModuleGroupDto.builder()
                            .id(idCounter.getAndIncrement())
                            .module(moduleDto)
                            .build();
                })
                .toList();
    }
}