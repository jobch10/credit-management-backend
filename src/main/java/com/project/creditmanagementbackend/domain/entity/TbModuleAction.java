package com.project.creditmanagementbackend.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "TbModuleAction")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TbModuleAction {

    @EmbeddedId
    private TbModuleActionId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("moduloId")
    @JoinColumn(name = "modulo_id")
    private TbModule modulo;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("accionId")
    @JoinColumn(name = "accion_id")
    private TbAction accion;

    @Builder.Default
    private boolean estado = true;

    @Column(name = "creado_en", updatable = false)
    private LocalDateTime creadoEn;

    @Column(name = "actualizado_en")
    private LocalDateTime actualizadoEn;

    @Column(name = "eliminado_en")
    private LocalDateTime eliminadoEn;

    @PrePersist
    protected void onCreate() {
        this.creadoEn = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.actualizadoEn = LocalDateTime.now();
    }
}
