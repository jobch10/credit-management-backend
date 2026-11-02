package com.project.creditmanagementbackend.domain.mapper;

import com.project.creditmanagementbackend.domain.dto.TbAuditoriaDto;
import com.project.creditmanagementbackend.domain.entity.TbAuditoria;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = { TbUserMapper.class })
public interface TbAuditoriaMapper {

    TbAuditoriaDto toDto(TbAuditoria entity);

    @Mapping(target = "deletedAt", ignore = true)
    TbAuditoria toEntity(TbAuditoriaDto dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    void updateEntityFromDto(TbAuditoriaDto dto, @MappingTarget TbAuditoria entity);
}
