package com.project.creditmanagementbackend.domain.enumerado;

public enum EnumTipoGarantia {

    PRENDARIA("prendaria"),
    VEHICULAR("vehicular"),
    INMOBILIARIA("inmobiliaria");

    private final String value;

    EnumTipoGarantia(String value) {
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