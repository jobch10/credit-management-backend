package com.project.creditmanagementbackend.domain.mapper;

import com.project.creditmanagementbackend.domain.dto.TbClientDto;
import com.project.creditmanagementbackend.domain.entity.TbClient;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {TbEntityMapper.class})
public interface TbClientMapper extends BaseMapper<TbClientDto, TbClient> {

    @Override
    @Mapping(target = "entityId", source = "entity.id")
    TbClientDto toDto(TbClient entity);

    @Override
    @Mapping(target = "entity", ignore = true)
    TbClient toEntity(TbClientDto dto);
}
