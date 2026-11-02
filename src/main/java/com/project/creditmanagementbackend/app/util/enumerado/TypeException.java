package com.project.creditmanagementbackend.app.util.enumerado;

public enum TypeException {
    I("Informativo"),
    A("Advertencia"),
    E("Error");

    private final String value;

    private TypeException(String value) {
        this.value = value;
    }

    public String toString() {
        return this.value;
    }

    public String getValue() {
        return this.value;
    }
}
