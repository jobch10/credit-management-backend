package com.project.creditmanagementbackend.app.dao;

import com.project.creditmanagementbackend.domain.entity.TbInstallment;
import com.project.creditmanagementbackend.domain.enumerado.EnumInstallmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface TbInstallmentDao extends JpaRepository<TbInstallment, Long>, JpaSpecificationExecutor<TbInstallment> {
    List<TbInstallment> findByLoanIdOrderByInstallmentNumberAsc(Long loanId);
    List<TbInstallment> findByLoanIdAndStatus(Long loanId, EnumInstallmentStatus status);
    Optional<TbInstallment> findByLoanIdAndInstallmentNumber(Long loanId, Integer installmentNumber);
}
