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
public class TbRolePermissionId implements Serializable {

    @Column(name = "tb_role_id")
    private Integer roleId;

    @Column(name = "tb_module_id")
    private Integer moduleId;

    @Column(name = "tb_action_id")
    private Integer actionId;
}
