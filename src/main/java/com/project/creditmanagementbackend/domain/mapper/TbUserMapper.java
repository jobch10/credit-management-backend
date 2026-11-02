package com.project.creditmanagementbackend.domain.mapper;

import com.project.creditmanagementbackend.domain.dto.request.RegisterRequestDto;
import com.project.creditmanagementbackend.domain.dto.TbUsuarioDto;
import com.project.creditmanagementbackend.domain.entity.TbUser;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = { TbRoleMapper.class }, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public abstract class TbUserMapper implements BaseMapper<TbUsuarioDto, TbUser> {

    @Override
    @Mapping(target = "role", source = "role")
    public abstract TbUsuarioDto toDto(TbUser entity);

    @Named("toSimpleUsuario")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "names", source = "names")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "username", source = "username")
    @Mapping(target = "createdAt", source = "createdAt")
    public abstract TbUsuarioDto toSimpleDto(TbUser entity);

    @Override
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    public abstract TbUser toEntity(TbUsuarioDto dto);

    @Mapping(target = "role", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "state", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "lastLoginAt", ignore = true)
    public abstract TbUser toEntity(RegisterRequestDto request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    public abstract void updateEntityFromDto(TbUsuarioDto dto, @MappingTarget TbUser entity);
}