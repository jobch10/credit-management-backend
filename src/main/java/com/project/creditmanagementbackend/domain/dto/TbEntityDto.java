package com.project.creditmanagementbackend.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.project.creditmanagementbackend.domain.enumerado.EnumTipoPersona;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TbEntityDto implements Serializable {
    private Integer id;
    private EnumTipoPersona entityType;
    private Integer typeDocumentId;
    private TbTypeDocumentDto typeDocument;
    private String documentNumber;
    private String legalName;
    private String tradeName;
    private String phone;
    private String email;
    private String address;
    private boolean state;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
