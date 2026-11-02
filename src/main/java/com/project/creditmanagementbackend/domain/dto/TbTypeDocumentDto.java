package com.project.creditmanagementbackend.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TbTypeDocumentDto implements Serializable {
    private Long id;
    private String code;
    private String name;
    private boolean state;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
