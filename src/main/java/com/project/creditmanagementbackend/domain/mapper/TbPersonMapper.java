package com.project.creditmanagementbackend.domain.mapper;

import com.project.creditmanagementbackend.domain.dto.TbPersonDto;
import com.project.creditmanagementbackend.domain.entity.TbPerson;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public abstract class TbPersonMapper implements BaseMapper<TbPersonDto, TbPerson> {

    @Override
    @Named("toFullPersonDto")
    @Mapping(target = "financialSubjectId", source = "financialSubject.id")
    public abstract TbPersonDto toDto(TbPerson entity);

    @Named("toBasicPersonDto")
    @Mapping(target = "financialSubjectId", source = "financialSubject.id")
    public abstract TbPersonDto toBasicDto(TbPerson entity);

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "financialSubject", ignore = true)
    public abstract TbPerson toEntity(TbPersonDto dto);

    @Override
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dni", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    public abstract void updateEntityFromDto(TbPersonDto dto, @MappingTarget TbPerson entity);
}
