package com.project.creditmanagementbackend.domain.enumerado;

public enum EnumMetodoCalculoInteres {

    INTERES_SIMPLE_MENSUAL("interes_simple_mensual"),
    CUOTA_FIJA("cuota_fija"),
    INTERES_MENSUAL_FIJO("interes_mensual_fijo");

    private final String value;

    EnumMetodoCalculoInteres(String value) {
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