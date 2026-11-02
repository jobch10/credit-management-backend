package com.project.creditmanagementbackend.app.dao;

import com.project.creditmanagementbackend.domain.entity.TbRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TbRoleDao extends JpaRepository<TbRole, Integer>, JpaSpecificationExecutor<TbRole> {
    Optional<TbRole> findByName(String name);

    boolean existsByName(String name);

    Optional<TbRole> findByCode(String code);

    boolean existsByCode(String code);

}
