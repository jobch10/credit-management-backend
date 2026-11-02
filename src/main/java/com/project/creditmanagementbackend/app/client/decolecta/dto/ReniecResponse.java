package com.project.creditmanagementbackend.app.client.decolecta.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ReniecResponse(
        @JsonProperty("first_name")       String firstName,
        @JsonProperty("first_last_name")  String firstLastName,
        @JsonProperty("second_last_name") String secondLastName,
        @JsonProperty("full_name")        String fullName,
        @JsonProperty("document_number")  String documentNumber
) {}