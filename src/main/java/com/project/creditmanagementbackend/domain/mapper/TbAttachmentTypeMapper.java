package com.project.creditmanagementbackend.domain.mapper;

import com.project.creditmanagementbackend.domain.dto.TbAttachmentTypeDto;
import com.project.creditmanagementbackend.domain.entity.TbAttachmentType;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface TbAttachmentTypeMapper extends BaseMapper<TbAttachmentTypeDto, TbAttachmentType> {

    @Override
    TbAttachmentTypeDto toDto(TbAttachmentType entity);

    @Override
    TbAttachmentType toEntity(TbAttachmentTypeDto dto);

    @Override
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    void updateEntityFromDto(TbAttachmentTypeDto dto, @MappingTarget TbAttachmentType entity);
}
