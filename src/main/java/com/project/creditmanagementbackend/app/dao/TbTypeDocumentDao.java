package com.project.creditmanagementbackend.app.dao;

import com.project.creditmanagementbackend.domain.entity.TbTypeDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TbTypeDocumentDao extends JpaRepository<TbTypeDocument, Integer>, JpaSpecificationExecutor<TbTypeDocument> {
    Optional<TbTypeDocument> findByCode(String code);
}
