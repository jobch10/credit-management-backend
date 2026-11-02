package com.project.creditmanagementbackend.app.dao;

import com.project.creditmanagementbackend.domain.entity.TbLoan;
import com.project.creditmanagementbackend.domain.enumerado.EnumLoanStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface TbLoanDao extends JpaRepository<TbLoan, Long>, JpaSpecificationExecutor<TbLoan> {
    Optional<TbLoan> findByLoanNumber(String loanNumber);
    boolean existsByLoanNumber(String loanNumber);
    List<TbLoan> findByBorrowerSubjectId(Long borrowerSubjectId);
    List<TbLoan> findByStatus(EnumLoanStatus status);
}