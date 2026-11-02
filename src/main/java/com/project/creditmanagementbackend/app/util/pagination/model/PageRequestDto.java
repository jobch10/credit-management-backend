package com.project.creditmanagementbackend.app.util.pagination.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO para captura de parámetros de paginación y ordenamiento")
public class PageRequestDto {

    @Schema(description = "Número de página (1-indexed)", example = "1")
    @Builder.Default
    private int page = 1;

    @Schema(description = "Elementos por página", example = "10")
    @Builder.Default
    private int perPage = 10;

    @Schema(description = "Campo por el cual ordenar", example = "id")
    private String sort;

    @Schema(description = "Dirección del ordenamiento (asc/desc)", example = "asc")
    private String direction;
}
