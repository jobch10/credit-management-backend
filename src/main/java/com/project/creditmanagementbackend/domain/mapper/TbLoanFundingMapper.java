package com.project.creditmanagementbackend.domain.mapper;

import com.project.creditmanagementbackend.domain.dto.TbLoanFundingDto;
import com.project.creditmanagementbackend.domain.entity.TbLoanFunding;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class TbLoanFundingMapper implements BaseMapper<TbLoanFundingDto, TbLoanFunding> {

    @Override
    @Named("toFullLoanFundingDto")
    @Mapping(target = "loanId", source = "loan.id")
    @Mapping(target = "investorSubjectId", source = "investorSubject.id")
    public abstract TbLoanFundingDto toDto(TbLoanFunding entity);

    @Named("toBasicLoanFundingDto")
    @Mapping(target = "loanId", source = "loan.id")
    @Mapping(target = "investorSubjectId", source = "investorSubject.id")
    public abstract TbLoanFundingDto toBasicDto(TbLoanFunding entity);

    @Named("toBasicLoanFundingDtoList")
    public abstract List<TbLoanFundingDto> toBasicDtoList(List<TbLoanFunding> entities);

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "loan", ignore = true)
    @Mapping(target = "investorSubject", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    public abstract TbLoanFunding toEntity(TbLoanFundingDto dto);

    @Override
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "loan", ignore = true)
    @Mapping(target = "investorSubject", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    public abstract void updateEntityFromDto(TbLoanFundingDto dto, @MappingTarget TbLoanFunding entity);
}