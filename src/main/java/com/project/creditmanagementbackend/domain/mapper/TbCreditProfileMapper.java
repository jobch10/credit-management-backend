package com.project.creditmanagementbackend.domain.mapper;

import com.project.creditmanagementbackend.domain.dto.TbCreditProfileDto;
import com.project.creditmanagementbackend.domain.entity.TbCreditProfile;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public abstract class TbCreditProfileMapper implements BaseMapper<TbCreditProfileDto, TbCreditProfile> {

    @Override
    @Named("toFullProfileDto")
    @Mapping(target = "financialSubjectId", source = "financialSubject.id")
    public abstract TbCreditProfileDto toDto(TbCreditProfile entity);

    @Named("toBasicProfileDto")
    @Mapping(target = "financialSubjectId", source = "financialSubject.id")
    public abstract TbCreditProfileDto toBasicDto(TbCreditProfile entity);

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "financialSubject", ignore = true)
    public abstract TbCreditProfile toEntity(TbCreditProfileDto dto);

    @Override
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "totalLoansApproved", ignore = true)
    @Mapping(target = "maxDaysInArrears", ignore = true)
    @Mapping(target = "timesRefinanced", ignore = true)
    @Mapping(target = "creditScore", ignore = true)
    @Mapping(target = "riskLevel", ignore = true)
    public abstract void updateEntityFromDto(TbCreditProfileDto dto, @MappingTarget TbCreditProfile entity);
}