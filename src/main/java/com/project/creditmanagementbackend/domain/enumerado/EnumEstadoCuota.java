package com.project.creditmanagementbackend.domain.enumerado;

public enum EnumEstadoCuota {

    PENDIENTE("pendiente"),
    PAGADA("pagada"),
    PARCIAL("parcial"),
    VENCIDA("vencida");

    private final String value;

    EnumEstadoCuota(String value) {
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