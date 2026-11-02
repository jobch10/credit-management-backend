package com.project.creditmanagementbackend.app.dao;

import com.project.creditmanagementbackend.domain.entity.TbModule;
import com.project.creditmanagementbackend.domain.enumerado.ModuleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TbModuleDao extends JpaRepository<TbModule, Integer>, JpaSpecificationExecutor<TbModule> {
    Optional<TbModule> findByCode(String code);

    boolean existsByCode(String code);

    boolean existsByCodeAndIdNot(String code, Integer id);

    Optional<TbModule> findByName(String name);

    boolean existsByName(String name);

    boolean existsByNameAndIdNot(String name, Integer id);

    List<TbModule> findByType(ModuleType type);

    long countByParentId(Integer parentId);
}
