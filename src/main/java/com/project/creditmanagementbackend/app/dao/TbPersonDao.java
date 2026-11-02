package com.project.creditmanagementbackend.app.dao;

import com.project.creditmanagementbackend.domain.entity.TbPerson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface TbPersonDao extends JpaRepository<TbPerson, Long>, JpaSpecificationExecutor<TbPerson> {
    Optional<TbPerson> findByDni(String dni);

    Optional<TbPerson> findByFinancialSubjectId(Long financialSubjectId);

    boolean existsByDni(String dni);

    boolean existsByDniAndIdNot(String dni, Long id);

    boolean existsByFinancialSubjectId(Long financialSubjectId);
}