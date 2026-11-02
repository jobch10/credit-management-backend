package com.project.creditmanagementbackend.domain.mapper;

import com.project.creditmanagementbackend.domain.dto.TbLoanDto;
import com.project.creditmanagementbackend.domain.entity.TbLoan;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {
        TbFinancialSubjectMapper.class,
        TbInstallmentMapper.class,
        TbLoanFundingMapper.class
})
public abstract class TbLoanMapper implements BaseMapper<TbLoanDto, TbLoan> {

    @Override
    @Named("toFullLoanDto")
    @Mapping(target = "borrowerSubjectId", source = "borrowerSubject.id")
    @Mapping(target = "installments", source = "installments", qualifiedByName = "toBasicInstallmentDtoList")
    @Mapping(target = "fundings", source = "fundings", qualifiedByName = "toBasicLoanFundingDtoList")
    public abstract TbLoanDto toDto(TbLoan entity);

    @Named("toBasicLoanDto")
    @Mapping(target = "borrowerSubjectId", source = "borrowerSubject.id")
    @Mapping(target = "installments", ignore = true)
    @Mapping(target = "fundings", ignore = true)
    public abstract TbLoanDto toBasicDto(TbLoan entity);

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "installments", ignore = true)
    @Mapping(target = "fundings", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    public abstract TbLoan toEntity(TbLoanDto dto);

    @Override
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "loanNumber", ignore = true)
    @Mapping(target = "installments", ignore = true)
    @Mapping(target = "fundings", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    public abstract void updateEntityFromDto(TbLoanDto dto, @MappingTarget TbLoan entity);
}