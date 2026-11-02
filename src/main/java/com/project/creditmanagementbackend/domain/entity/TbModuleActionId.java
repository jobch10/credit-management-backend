package com.project.creditmanagementbackend.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class TbModuleActionId implements Serializable {

    @Column(name = "modulo_id")
    private Integer moduloId;

    @Column(name = "accion_id")
    private Integer accionId;
}
