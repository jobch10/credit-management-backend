package com.project.creditmanagementbackend.domain.mapper;

import com.project.creditmanagementbackend.domain.dto.TbInvestorDto;
import com.project.creditmanagementbackend.domain.entity.TbInvestor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {TbEntityMapper.class})
public interface TbInvestorMapper extends BaseMapper<TbInvestorDto, TbInvestor> {

    @Override
    @Mapping(target = "entityId", source = "entity.id")
    TbInvestorDto toDto(TbInvestor entity);

    @Override
    @Mapping(target = "entity", ignore = true)
    TbInvestor toEntity(TbInvestorDto dto);
}
