package com.project.creditmanagementbackend.app.dao;

import com.project.creditmanagementbackend.domain.entity.TbCreditProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TbCreditProfileDao extends JpaRepository<TbCreditProfile, Long>, JpaSpecificationExecutor<TbCreditProfile> {
    Optional<TbCreditProfile> findByFinancialSubjectId(Long financialSubjectId);

    boolean existsByFinancialSubjectId(Long financialSubjectId);
}