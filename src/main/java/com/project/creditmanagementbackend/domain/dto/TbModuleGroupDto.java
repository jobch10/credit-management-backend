package com.project.creditmanagementbackend.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import jakarta.validation.constraints.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TbModuleGroupDto implements Serializable {
    private Integer id;
    private ModuleGroupDto module;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class ModuleGroupDto {
        private Integer id;

        @NotBlank(message = "El código es obligatorio")
        @Size(max = 20, message = "El código no puede superar los 20 caracteres")
        private String code;

        @NotBlank(message = "El nombre es obligatorio")
        @Size(max = 100, message = "El nombre no puede superar los 100 caracteres")
        private String name;

        private boolean state;

        private List<ActionGroupDto> actions;

        @Data
        @Builder
        @NoArgsConstructor
        @AllArgsConstructor
        @JsonInclude(JsonInclude.Include.NON_NULL)
        public static class ActionGroupDto {
            private Integer id;

            @NotBlank(message = "El código es obligatorio")
            @Size(max = 20, message = "El código no puede superar los 20 caracteres")
            private String code;

            @NotBlank(message = "El nombre es obligatorio")
            @Size(max = 100, message = "El nombre no puede superar los 100 caracteres")
            private String name;

            private boolean state;
        }
    }
}
