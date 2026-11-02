package com.project.creditmanagementbackend.domain.enumerado;

public enum EnumTipoPersona {

    NATURAL("natural"),
    JURIDICA("juridica");

    private final String value;

    EnumTipoPersona(String value) {
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