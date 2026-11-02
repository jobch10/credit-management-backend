package com.project.creditmanagementbackend.domain.enumerado;

public enum EnumEstadoRegistro {

    ACTIVO("activo"),
    INACTIVO("inactivo");

    private final String value;

    EnumEstadoRegistro(String value) {
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