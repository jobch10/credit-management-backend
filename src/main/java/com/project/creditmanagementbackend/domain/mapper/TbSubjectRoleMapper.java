package com.project.creditmanagementbackend.domain.mapper;

import com.project.creditmanagementbackend.domain.dto.TbSubjectRoleDto;
import com.project.creditmanagementbackend.domain.entity.TbSubjectRole;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class TbSubjectRoleMapper implements BaseMapper<TbSubjectRoleDto, TbSubjectRole> {

    @Override
    @Named("toFullRoleDto")
    @Mapping(target = "financialSubjectId", source = "financialSubject.id")
    public abstract TbSubjectRoleDto toDto(TbSubjectRole entity);

    @Named("toBasicRoleDto")
    @Mapping(target = "financialSubjectId", source = "financialSubject.id")
    public abstract TbSubjectRoleDto toBasicDto(TbSubjectRole entity);

    @Named("toBasicRoleDtoList")
    public abstract List<TbSubjectRoleDto> toBasicDtoList(List<TbSubjectRole> entities);

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "financialSubject", ignore = true)
    public abstract TbSubjectRole toEntity(TbSubjectRoleDto dto);

    @Override
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    public abstract void updateEntityFromDto(TbSubjectRoleDto dto, @MappingTarget TbSubjectRole entity);
}