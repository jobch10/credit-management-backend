package com.project.creditmanagementbackend.app.dao;

import com.project.creditmanagementbackend.domain.entity.TbModuleAction;
import com.project.creditmanagementbackend.domain.entity.TbModuleActionId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TbModuleActionDao extends JpaRepository<TbModuleAction, TbModuleActionId> {

    List<TbModuleAction> findByIdModuloIdIn(List<Integer> moduloIds);

    @Query(value = "SELECT * FROM tb_modulo_accion WHERE modulo_id IN (:moduloIds)", nativeQuery = true)
    List<TbModuleAction> findAllByModuloIdInIncludeDeleted(@Param("moduloIds") List<Integer> moduloIds);

    boolean existsByIdModuloIdAndEstadoTrue(Integer moduloId);

    // # <========= OBTENER IDS DE ACCIONES ACTIVAS ASOCIADAS A UN MÓDULO =========>
    @Query("SELECT ma.accion.id FROM TbModuleAction ma WHERE ma.modulo.id = :moduleId AND ma.estado = true")
    List<Long> findActionIdsByModuleId(@Param("moduleId") Integer moduleId);

    // # <========= ELIMINAR LOS REGISTROS QUE CONTENGAN ESE MÓDULO =========>
    void deleteByIdModuloId(Integer moduloId);

    // # <========= VERIFICAR SI UNA ACCIÓN ESTÁ ASIGNADA A UN MÓDULO =========>
    boolean existsByIdModuloIdAndIdAccionId(Integer moduloId, Integer actionId);

    // # <========= VERIFICAR DEPENDENCIAS =========>
    long countByAccionId(Integer accionId);
    long countByModuloId(Integer moduloId);

    // # <========= ELIMINAR LOS REGISTROS QUE CONTENGAN ESA ACCION =========>
    void deleteByAccionId(Integer accionId);
}
