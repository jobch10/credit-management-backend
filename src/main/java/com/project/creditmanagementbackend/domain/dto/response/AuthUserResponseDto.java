package com.project.creditmanagementbackend.domain.dto.response;

import com.project.creditmanagementbackend.domain.dto.TbModuleDto;
import lombok.Data;

import java.util.List;
import jakarta.validation.constraints.*;

@Data
public class AuthUserResponseDto {
    private UserDto user;
    private RoleDto role;
    private List<TbModuleDto> permissionModules;
    private String path;

    @Data
    public static class UserDto {
        private Integer id;

        @NotBlank(message = "Los nombres son obligatorios")
        @Size(max = 100, message = "Los nombres no pueden superar los 100 caracteres")
        private String names;

        @NotBlank(message = "El correo es obligatorio")
        @Email(message = "Debe ser un correo válido")
        @Size(max = 100, message = "El correo no puede superar los 100 caracteres")
        private String email;

        @NotBlank(message = "El nombre de usuario es obligatorio")
        @Size(max = 100, message = "El nombre de usuario no puede superar los 100 caracteres")
        private String username;

        private boolean state;
    }

    @Data
    public static class RoleDto {
        private Integer id;

        @NotBlank(message = "El nombre es obligatorio")
        @Size(max = 100, message = "El nombre no puede superar los 100 caracteres")
        private String name;

        @NotBlank(message = "El código es obligatorio")
        @Size(max = 20, message = "El código no puede superar los 20 caracteres")
        private String code;
    }

    @Data
    public static class PermissionDto {
        private Integer id;
        private ModuleDto module;

        @Data
        public static class ModuleDto {
            private Integer id;

            @NotBlank(message = "El código es obligatorio")
            @Size(max = 20, message = "El código no puede superar los 20 caracteres")
            private String code;

            @NotBlank(message = "El nombre es obligatorio")
            @Size(max = 100, message = "El nombre no puede superar los 100 caracteres")
            private String name;

            private boolean state;

            private List<ActionDto> actions;

            @Data
            public static class ActionDto {
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
}