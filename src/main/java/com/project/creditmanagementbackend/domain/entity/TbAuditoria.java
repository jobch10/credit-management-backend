package com.project.creditmanagementbackend.domain.entity;

import com.project.creditmanagementbackend.domain.enumerado.EnumMovimientoAuditoria;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDateTime;

@Entity
@Table(name = "TbAuditoria")
@SQLDelete(sql = "UPDATE tb_auditoria SET deleted_at = NOW() WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TbAuditoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "usuario_id")
    private TbUser usuario;

    @Enumerated(EnumType.STRING)
    private EnumMovimientoAuditoria tipo;

    private String entidad;

    @Column(name = "entidad_id")
    private Integer entidadId;

    @Column(name = "antes_json", columnDefinition = "TEXT")
    private String antesJson;

    @Column(name = "despues_json", columnDefinition = "TEXT")
    private String despuesJson;

    private String ip;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

}