package com.project.creditmanagementbackend.app.dao;

import com.project.creditmanagementbackend.domain.entity.TbEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TbEntityDao extends JpaRepository<TbEntity, Integer>, JpaSpecificationExecutor<TbEntity> {
    Optional<TbEntity> findByDocumentNumber(String documentNumber);
}
