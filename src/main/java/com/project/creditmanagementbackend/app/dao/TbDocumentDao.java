package com.project.creditmanagementbackend.app.dao;

import com.project.creditmanagementbackend.domain.entity.TbDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TbDocumentDao extends JpaRepository<TbDocument, Integer>, JpaSpecificationExecutor<TbDocument> {
    List<TbDocument> findByEntityAndEntityId(String entity, Integer entityId);
    boolean existsByDocumentType(String documentType);
}
