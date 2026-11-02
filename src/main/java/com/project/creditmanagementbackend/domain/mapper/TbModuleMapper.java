package com.project.creditmanagementbackend.domain.mapper;

import com.project.creditmanagementbackend.domain.dto.TbActionDto;
import com.project.creditmanagementbackend.domain.dto.TbModuleDto;
import com.project.creditmanagementbackend.domain.entity.TbModule;
import com.project.creditmanagementbackend.domain.entity.TbModuleAction;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.List;

@Mapper(componentModel = "spring")
public abstract class TbModuleMapper implements BaseMapper<TbModuleDto, TbModule> {

    protected TbActionMapper actionMapper;

    @Autowired
    public void setActionMapper(TbActionMapper actionMapper) {
        this.actionMapper = actionMapper;
    }

    @Override
    @Named("toFullDto")
    @Mapping(target = "parentId", source = "parent.id")
    @Mapping(target = "actions", source = "actions")
    public abstract TbModuleDto toDto(TbModule entity);

    @Named("toDtoWithActions")
    @Mapping(target = "parentId", source = "parent.id")
    @Mapping(target = "actions", source = "actions")
    @Mapping(target = "children", ignore = true)
    public abstract TbModuleDto toDtoWithActions(TbModule entity);

    @Named("toBasicDto")
    @Mapping(target = "parentId", source = "parent.id")
    @Mapping(target = "actions", ignore = true)
    @Mapping(target = "children", ignore = true)
    public abstract TbModuleDto toBasicDto(TbModule entity);

    @Override
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "actions", ignore = true)
    @Mapping(target = "parent", ignore = true)
    @Mapping(target = "children", ignore = true)
    public abstract TbModule toEntity(TbModuleDto dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "actions", ignore = true)
    @Mapping(target = "parent", ignore = true)
    @Mapping(target = "children", ignore = true)
    public abstract void updateEntityFromDto(TbModuleDto dto, @MappingTarget TbModule entity);

    @IterableMapping(qualifiedByName = "toBasicDto")
    public abstract List<TbModuleDto> mapChildren(List<TbModule> entities);

    protected List<TbActionDto> mapActions(List<TbModuleAction> moduloActions) {
        if (moduloActions == null) return Collections.emptyList();

        return moduloActions.stream()
                .filter(TbModuleAction::isEstado)
                .map(ma -> actionMapper.toDto(ma.getAccion()))
                .toList();
    }
}