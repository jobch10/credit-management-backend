package com.project.creditmanagementbackend.domain.mapper;

import com.project.creditmanagementbackend.domain.dto.TbFinancialSubjectDto;
import com.project.creditmanagementbackend.domain.entity.TbFinancialSubject;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {
        TbPersonMapper.class,
        TbSubjectRoleMapper.class,
        TbCreditProfileMapper.class,
        TbInvestorProfileMapper.class,
})
public abstract class TbFinancialSubjectMapper implements BaseMapper<TbFinancialSubjectDto, TbFinancialSubject> {

    @Override
    @Named("toFullSubjectDto")
    @Mapping(target = "person", source = "person", qualifiedByName = "toBasicPersonDto")
    @Mapping(target = "roles", source = "roles", qualifiedByName = "toBasicRoleDtoList")
    @Mapping(target = "creditProfile", source = "creditProfile", qualifiedByName = "toBasicProfileDto")
    @Mapping(target = "investorProfile", source = "investorProfile", qualifiedByName = "toBasicInvestorProfileDto")
    public abstract TbFinancialSubjectDto toDto(TbFinancialSubject entity);

    @Named("toBasicSubjectDto")
    @Mapping(target = "person", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "creditProfile", ignore = true)
    @Mapping(target = "investorProfile", ignore = true)
    public abstract TbFinancialSubjectDto toBasicDto(TbFinancialSubject entity);

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "person", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "creditProfile", ignore = true)
    @Mapping(target = "investorProfile", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    public abstract TbFinancialSubject toEntity(TbFinancialSubjectDto dto);

    @Override
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "person", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "creditProfile", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    public abstract void updateEntityFromDto(TbFinancialSubjectDto dto, @MappingTarget TbFinancialSubject entity);
}