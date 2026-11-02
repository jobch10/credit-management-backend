package com.project.creditmanagementbackend.app.dao;

import com.project.creditmanagementbackend.domain.entity.TbAttachmentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TbAttachmentTypeDao extends JpaRepository<TbAttachmentType, Integer> {
    Optional<TbAttachmentType> findByCode(String code);
    boolean existsByCode(String code);
}
