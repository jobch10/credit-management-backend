package com.project.creditmanagementbackend.domain.mapper;

import com.project.creditmanagementbackend.domain.dto.TbPaymentDto;
import com.project.creditmanagementbackend.domain.entity.TbPayment;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class TbPaymentMapper implements BaseMapper<TbPaymentDto, TbPayment> {

    @Override
    @Named("toFullPaymentDto")
    @Mapping(target = "loanId", source = "loan.id")
    @Mapping(target = "installmentId", source = "installment.id")
    public abstract TbPaymentDto toDto(TbPayment entity);

    @Named("toBasicPaymentDto")
    @Mapping(target = "loanId", source = "loan.id")
    @Mapping(target = "installmentId", source = "installment.id")
    public abstract TbPaymentDto toBasicDto(TbPayment entity);

    @Named("toBasicPaymentDtoList")
    public abstract List<TbPaymentDto> toBasicDtoList(List<TbPayment> entities);

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "loan", ignore = true)
    @Mapping(target = "installment", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    public abstract TbPayment toEntity(TbPaymentDto dto);

    @Override
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "loan", ignore = true)
    @Mapping(target = "installment", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    public abstract void updateEntityFromDto(TbPaymentDto dto, @MappingTarget TbPayment entity);
}