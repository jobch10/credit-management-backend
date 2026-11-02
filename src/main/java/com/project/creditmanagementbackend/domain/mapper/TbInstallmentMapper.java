package com.project.creditmanagementbackend.domain.mapper;

import com.project.creditmanagementbackend.domain.dto.TbInstallmentDto;
import com.project.creditmanagementbackend.domain.entity.TbInstallment;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class TbInstallmentMapper implements BaseMapper<TbInstallmentDto, TbInstallment> {

    @Override
    @Named("toFullInstallmentDto")
    @Mapping(target = "loanId", source = "loan.id")
    public abstract TbInstallmentDto toDto(TbInstallment entity);

    @Named("toBasicInstallmentDto")
    @Mapping(target = "loanId", source = "loan.id")
    public abstract TbInstallmentDto toBasicDto(TbInstallment entity);

    @Named("toBasicInstallmentDtoList")
    public abstract List<TbInstallmentDto> toBasicDtoList(List<TbInstallment> entities);

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "loan", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    public abstract TbInstallment toEntity(TbInstallmentDto dto);

    @Override
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "loan", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    public abstract void updateEntityFromDto(TbInstallmentDto dto, @MappingTarget TbInstallment entity);
}