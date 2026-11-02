package com.project.creditmanagementbackend.app.dao;

import com.project.creditmanagementbackend.domain.entity.TbPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface TbPaymentDao extends JpaRepository<TbPayment, Long>, JpaSpecificationExecutor<TbPayment> {
    List<TbPayment> findByLoanIdOrderByPaymentDateDesc(Long loanId);
    List<TbPayment> findByInstallmentId(Long installmentId);
}