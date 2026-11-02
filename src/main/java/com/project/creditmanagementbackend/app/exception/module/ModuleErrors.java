package com.project.creditmanagementbackend.app.exception.module;

import com.project.creditmanagementbackend.app.exception.ApiError;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import com.project.creditmanagementbackend.app.util.enumerado.TypeException;
import static com.project.creditmanagementbackend.app.util.enumerado.TypeException.*;

@Getter
public enum ModuleErrors implements ApiError {
    MODULO_NOT_FOUND("MOD_001", "", "Módulo no encontrado", HttpStatus.NOT_FOUND, E),
    MODULO_ALREADY_EXISTS("MOD_002", "", "El nombre del módulo ya existe", HttpStatus.CONFLICT,E),
    MODULO_CODE_ALREADY_EXISTS("MOD_003", "", "El código del módulo ya existe", HttpStatus.CONFLICT, E),
    MODULO_HAS_CHILDREN("MOD_004",  "El módulo contiene submódulos", "El módulo no puede ser eliminado porque tiene submódulos", HttpStatus.CONFLICT, E),
    GROUP_CANNOT_HAVE_ROUTE("MOD_005", "Error de módulo", "Un módulo de tipo GRUPO no puede tener una ruta asignada", HttpStatus.BAD_REQUEST, E),
    MODULE_ROUTE_REQUIRED("MOD_006", "", "Un módulo de tipo MÓDULO debe tener una ruta asignada", HttpStatus.BAD_REQUEST, E),
    MODULO_CANNOT_BE_OWN_PARENT("MOD_007", "", "Un módulo no puede ser su propio padre", HttpStatus.BAD_REQUEST, E),
    MODULO_PARENT_INACTIVE("MOD_008", "", "El módulo padre está inactivo", HttpStatus.BAD_REQUEST, E),
    MODULO_HIERARCHY_CYCLE("MOD_009", "", "La jerarquía de módulos no puede contener ciclos", HttpStatus.BAD_REQUEST, E),
    MODULO_PARENT_NOT_FOUND("MOD_010", "", "El módulo padre no existe", HttpStatus.BAD_REQUEST, E),
    MODULO_HAS_ACTIVE_ACTIONS("MOD_011", "", "El módulo no puede ser eliminado porque tiene actions activas", HttpStatus.CONFLICT, E),
    MODULO_HAS_ROLE_PERMISSIONS("MOD_012", "Dependencias", "El módulo no puede ser eliminado porque está asociado a permisos de roles", HttpStatus.CONFLICT, E);

    private final String code;
    private final String title;
    private final String message;
    private final HttpStatus status;
    private final TypeException type;

    ModuleErrors(String code, String title, String message, HttpStatus status, TypeException type) {
        this.code = code;
        this.title = title;
        this.message = message;
        this.status = status;
        this.type = type;
    }
}