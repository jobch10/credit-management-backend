package com.project.creditmanagementbackend.domain.mapper;

import com.project.creditmanagementbackend.domain.dto.TbActionDto;
import com.project.creditmanagementbackend.domain.entity.TbAction;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public abstract class TbActionMapper implements BaseMapper<TbActionDto, TbAction> {

    public abstract TbActionDto toDto(TbAction entity);

    @Mapping(target = "deletedAt", ignore = true)
    public abstract TbAction toEntity(TbActionDto dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    public abstract void updateEntityFromDto(TbActionDto dto, @MappingTarget TbAction entity);
}