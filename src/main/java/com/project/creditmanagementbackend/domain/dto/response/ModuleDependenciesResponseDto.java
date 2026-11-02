package com.project.creditmanagementbackend.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ModuleDependenciesResponseDto {
    private boolean hasDependencies;
    private long rolePermissionsCount;
    private long moduleActionsCount;
    private long childrenCount;
}
