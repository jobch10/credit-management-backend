package com.project.creditmanagementbackend.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActionDependenciesResponseDto {
    private boolean hasDependencies;
    private long moduleActionsCount;
    private long rolePermissionsCount;
}