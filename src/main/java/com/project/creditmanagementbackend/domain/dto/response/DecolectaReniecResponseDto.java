package com.project.creditmanagementbackend.domain.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class DecolectaReniecResponseDto {
    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("first_last_name")
    private String firstLastName;

    @JsonProperty("second_last_name")
    private String secondLastName;

    @JsonProperty("document_number")
    private String documentNumber;
}
