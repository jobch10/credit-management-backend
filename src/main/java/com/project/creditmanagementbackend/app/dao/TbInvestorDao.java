package com.project.creditmanagementbackend.app.dao;

import com.project.creditmanagementbackend.domain.entity.TbInvestor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.project.creditmanagementbackend.app.dao.custom.TbInvestorCustomDao;
import java.util.Optional;

@Repository
public interface TbInvestorDao extends JpaRepository<TbInvestor, Integer>, JpaSpecificationExecutor<TbInvestor>, TbInvestorCustomDao {
    Optional<TbInvestor> findByInvestorCode(String investorCode);
    boolean existsByInvestorCode(String investorCode);
}
