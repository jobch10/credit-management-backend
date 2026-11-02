package com.project.creditmanagementbackend.domain.enumerado;

public enum EnumSubjectType {
    PERSONA("PERSONA"),
    EMPRESA("EMPRESA");

    private final String value;

    EnumSubjectType(String value) {
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
