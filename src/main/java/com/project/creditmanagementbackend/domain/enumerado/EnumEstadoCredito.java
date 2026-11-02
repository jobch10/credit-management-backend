package com.project.creditmanagementbackend.domain.enumerado;

public enum EnumEstadoCredito {

    BORRADOR("borrador"),
    EN_EVALUACION("en_evaluacion"),
    APROBADO("aprobado"),
    ACTIVO("activo"),
    VENCIDO("vencido"),
    EN_COBRANZA("en_cobranza"),
    EJECUTADO("ejecutado"),
    CERRADO("cerrado"),
    CANCELADO("cancelado");

    private final String value;

    EnumEstadoCredito(String value) {
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