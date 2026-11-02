package com.project.creditmanagementbackend.domain.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Set;

@Data
public class UpdateModuleActionsRequestDto {
    @NotNull(message = "La lista de IDs de acciones es obligatoria")
    private Set<Integer> actionIds;
}