package com.project.creditmanagementbackend.app.dao;

import com.project.creditmanagementbackend.domain.entity.TbAction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TbActionDao extends JpaRepository<TbAction, Integer>, JpaSpecificationExecutor<TbAction> {
    Optional<TbAction> findByCode(String code);

    boolean existsByCode(String code);

    Optional<TbAction> findByName(String name);

    boolean existsByName(String name);
}
