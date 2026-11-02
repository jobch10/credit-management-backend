package com.project.creditmanagementbackend.domain.enumerado;

public enum EnumRiskTolerance {
    CONSERVADOR("CONSERVADOR"),
    MODERADO("MODERADO"),
    AGRESIVO("AGRESIVO");

    private final String value;
    EnumRiskTolerance(String value) { this.value = value; }
    public String getValue() { return value; }
    @Override public String toString() { return value; }
}
