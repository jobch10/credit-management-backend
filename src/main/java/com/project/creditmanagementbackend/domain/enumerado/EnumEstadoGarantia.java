package com.project.creditmanagementbackend.domain.enumerado;

public enum EnumEstadoGarantia {

    VIGENTE("vigente"),
    LIBERADA("liberada"),
    EN_EJECUCION("en_ejecucion"),
    EJECUTADA("ejecutada");

    private final String value;

    EnumEstadoGarantia(String value) {
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