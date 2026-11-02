package com.project.creditmanagementbackend.domain.enumerado;

public enum EnumMovimientoAuditoria {

    CREAR("crear"),
    EDITAR("editar"),
    ELIMINAR("eliminar"),
    APROBAR("aprobar"),
    ANULAR("anular"),
    INICIAR_SESION("iniciar_sesion"),
    CERRAR_SESION("cerrar_sesion");

    private final String value;

    EnumMovimientoAuditoria(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }
}