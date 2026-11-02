package com.project.creditmanagementbackend.domain.enumerado;

public enum EnumTipoPagoInversionista {

    MENSUAL("mensual"),
    AL_FINAL("al_final");

    private final String value;

    EnumTipoPagoInversionista(String value) {
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