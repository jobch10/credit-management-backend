package com.project.creditmanagementbackend.domain.enumerado;

public enum EnumSubjectStatus {
    ACTIVO("ACTIVO"),
    INACTIVO("INACTIVO"),
    BLOQUEADO("BLOQUEADO");

    private final String value;

    EnumSubjectStatus(String value) {
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
