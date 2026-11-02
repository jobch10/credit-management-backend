package com.project.creditmanagementbackend.app.dao;

import com.project.creditmanagementbackend.domain.entity.TbSubjectRole;
import com.project.creditmanagementbackend.domain.enumerado.EnumSubjectRole;
import com.project.creditmanagementbackend.domain.enumerado.EnumSubjectType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TbSubjectRoleDao
        extends JpaRepository<TbSubjectRole, Long>,
        JpaSpecificationExecutor<TbSubjectRole> {

    List<TbSubjectRole> findByFinancialSubjectId(Long financialSubjectId);

    List<TbSubjectRole> findByRole(EnumSubjectRole role);

    List<TbSubjectRole> findByRoleAndFinancialSubject_SubjectType(EnumSubjectRole role, EnumSubjectType subjectType);

    Optional<TbSubjectRole> findByFinancialSubjectIdAndRole(Long financialSubjectId, EnumSubjectRole role);

    boolean existsByFinancialSubjectIdAndRole(Long financialSubjectId, EnumSubjectRole role);
}