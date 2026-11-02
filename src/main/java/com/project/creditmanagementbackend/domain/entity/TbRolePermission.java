package com.project.creditmanagementbackend.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "TbRolePermission")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TbRolePermission {

    @EmbeddedId
    private TbRolePermissionId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("roleId")
    @JoinColumn(name = "tb_role_id")
    private TbRole role;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("moduleId")
    @JoinColumn(name = "tb_module_id")
    private TbModule module;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("actionId")
    @JoinColumn(name = "tb_action_id")
    private TbAction action;

    @Builder.Default
    private boolean state = true;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
