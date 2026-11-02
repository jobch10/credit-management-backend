package com.project.creditmanagementbackend.domain.mapper;

import com.project.creditmanagementbackend.domain.dto.TbEntityDto;
import com.project.creditmanagementbackend.domain.entity.TbEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {TbTypeDocumentMapper.class})
public interface TbEntityMapper extends BaseMapper<TbEntityDto, TbEntity> {

    @Override
    @Mapping(target = "typeDocumentId", source = "typeDocument.id")
    TbEntityDto toDto(TbEntity entity);

    @Override
    @Mapping(target = "typeDocument", ignore = true)
    TbEntity toEntity(TbEntityDto dto);
}
