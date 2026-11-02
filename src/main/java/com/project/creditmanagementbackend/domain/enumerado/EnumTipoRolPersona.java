package com.project.creditmanagementbackend.domain.enumerado;

public enum EnumTipoRolPersona {

    CLIENTE("cliente"),
    INVERSIONISTA("inversionista"),
    COLOCADOR("colocador"),
    COBRADOR("cobrador"),
    GARANTE("garante"),
    REPRESENTANTE_LEGAL("representante_legal");

    private final String value;

    EnumTipoRolPersona(String value) {
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