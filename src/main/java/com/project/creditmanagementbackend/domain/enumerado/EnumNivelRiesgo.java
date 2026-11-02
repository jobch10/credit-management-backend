package com.project.creditmanagementbackend.domain.enumerado;

public enum EnumNivelRiesgo {

    BAJO("bajo"),
    MEDIO("medio"),
    ALTO("alto");

    private final String value;

    EnumNivelRiesgo(String value) {
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
