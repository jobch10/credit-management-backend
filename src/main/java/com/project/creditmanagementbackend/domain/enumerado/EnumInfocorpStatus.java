package com.project.creditmanagementbackend.domain.enumerado;

public enum EnumInfocorpStatus {

    NORMAL("NORMAL"),
    CPP("CPP"),
    DUDOSO("DUDOSO"),
    PERDIDA("PERDIDA");

    private final String value;

    EnumInfocorpStatus(String value) {
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