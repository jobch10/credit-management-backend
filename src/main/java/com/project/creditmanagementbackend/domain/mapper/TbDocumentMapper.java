package com.project.creditmanagementbackend.domain.mapper;

import com.project.creditmanagementbackend.domain.dto.TbDocumentDto;
import com.project.creditmanagementbackend.domain.entity.TbDocument;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface TbDocumentMapper extends BaseMapper<TbDocumentDto, TbDocument> {

    @Override
    TbDocumentDto toDto(TbDocument entity);

    @Override
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    TbDocument toEntity(TbDocumentDto dto);

    @Override
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntityFromDto(TbDocumentDto dto, @MappingTarget TbDocument entity);
}
