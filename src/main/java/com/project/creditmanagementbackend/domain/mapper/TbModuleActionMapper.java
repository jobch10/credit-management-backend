package com.project.creditmanagementbackend.domain.mapper;

import com.project.creditmanagementbackend.domain.entity.TbAction;
import com.project.creditmanagementbackend.domain.entity.TbModule;
import com.project.creditmanagementbackend.domain.entity.TbModuleAction;
import com.project.creditmanagementbackend.domain.entity.TbModuleActionId;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public abstract class TbModuleActionMapper {

    public TbModuleAction toEntity(TbModule module, TbAction action, LocalDateTime now) {
        return TbModuleAction.builder()
                .id(new TbModuleActionId(module.getId(), action.getId()))
                .modulo(module)
                .accion(action)
                .estado(true)
                .creadoEn(now)
                .build();
    }
}
