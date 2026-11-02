package com.project.creditmanagementbackend.app.dao;

import com.project.creditmanagementbackend.domain.entity.TbRolePermission;
import com.project.creditmanagementbackend.domain.entity.TbRolePermissionId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TbRolePermissionDao extends JpaRepository<TbRolePermission, TbRolePermissionId> {
    List<TbRolePermission> findByRoleCode(String roleCode);

    List<TbRolePermission> findAllByRoleId(Integer roleId);

    // # <========= Eliminar Permisos por idRol =========>
    void deleteAllByRoleId(Integer roleId);

    // # <========= VERIFICAR DEPENDENCIAS =========>
    long countByActionId(Integer actionId);
    long countByModuleId(Integer moduleId);

    // # <========= ELIMINAR =========>
    void deleteByActionId(Integer actionId);
}
