package com.project.creditmanagementbackend.app.dao;

import com.project.creditmanagementbackend.domain.entity.TbFinancialSubject;
import com.project.creditmanagementbackend.domain.enumerado.EnumSubjectStatus;
import com.project.creditmanagementbackend.domain.enumerado.EnumSubjectType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TbFinancialSubjectDao extends JpaRepository<TbFinancialSubject, Long>, JpaSpecificationExecutor<TbFinancialSubject> {

    List<TbFinancialSubject> findBySubjectType(EnumSubjectType subjectType);

    List<TbFinancialSubject> findByStatus(EnumSubjectStatus status);

    boolean existsByEmail(String email);
}