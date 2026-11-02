package com.project.creditmanagementbackend.app.dao;

import com.project.creditmanagementbackend.domain.entity.TbLoanFunding;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface TbLoanFundingDao extends JpaRepository<TbLoanFunding, Long>, JpaSpecificationExecutor<TbLoanFunding> {
    List<TbLoanFunding> findByLoanId(Long loanId);
    List<TbLoanFunding> findByInvestorSubjectId(Long investorSubjectId);
    boolean existsByLoanIdAndInvestorSubjectId(Long loanId, Long investorSubjectId);
}