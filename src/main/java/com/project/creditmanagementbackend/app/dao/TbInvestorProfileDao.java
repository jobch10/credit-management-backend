package com.project.creditmanagementbackend.app.dao;

import com.project.creditmanagementbackend.domain.entity.TbInvestorProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TbInvestorProfileDao extends JpaRepository<TbInvestorProfile, Long>, JpaSpecificationExecutor<TbInvestorProfile> {

    Optional<TbInvestorProfile> findByFinancialSubjectId(Long financialSubjectId);

    boolean existsByFinancialSubjectId(Long financialSubjectId);
}