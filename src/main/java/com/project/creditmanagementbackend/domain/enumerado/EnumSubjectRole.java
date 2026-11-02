package com.project.creditmanagementbackend.domain.enumerado;

public enum EnumSubjectRole {
    PRESTATARIO("PRESTATARIO"),
    INVERSIONISTA("INVERSIONISTA"),
    GARANTE("GARANTE");

    private final String value;

    EnumSubjectRole(String value) {
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
