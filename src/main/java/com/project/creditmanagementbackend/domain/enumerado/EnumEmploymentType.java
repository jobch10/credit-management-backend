package com.project.creditmanagementbackend.domain.enumerado;

public enum EnumEmploymentType {
    DEPENDIENTE("DEPENDIENTE"),
    INDEPENDIENTE("INDEPENDIENTE"),
    JUBILADO("JUBILADO"),
    DESEMPLEADO("DESEMPLEADO");

    private final String value;

    EnumEmploymentType(String value) {
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