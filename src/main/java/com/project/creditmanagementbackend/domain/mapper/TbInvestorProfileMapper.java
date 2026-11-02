package com.project.creditmanagementbackend.domain.mapper;

import com.project.creditmanagementbackend.domain.dto.TbInvestorProfileDto;
import com.project.creditmanagementbackend.domain.entity.TbInvestorProfile;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {TbFinancialSubjectMapper.class})
public abstract class TbInvestorProfileMapper implements BaseMapper<TbInvestorProfileDto, TbInvestorProfile> {

    @Override
    @Named("toFullInvestorProfileDto")
    @Mapping(target = "financialSubjectId", source = "financialSubject.id")
    public abstract TbInvestorProfileDto toDto(TbInvestorProfile entity);

    @Named("toBasicInvestorProfileDto")
    @Mapping(target = "financialSubjectId", source = "financialSubject.id")
    public abstract TbInvestorProfileDto toBasicDto(TbInvestorProfile entity);

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "totalInvested", ignore = true)
    @Mapping(target = "activeInvestments", ignore = true)
    public abstract TbInvestorProfile toEntity(TbInvestorProfileDto dto);

    @Override
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "totalInvested", ignore = true)
    @Mapping(target = "activeInvestments", ignore = true)
    public abstract void updateEntityFromDto(TbInvestorProfileDto dto, @MappingTarget TbInvestorProfile entity);
}