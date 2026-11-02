package com.project.creditmanagementbackend.app.dao.custom.impl;

import com.project.creditmanagementbackend.app.dao.custom.TbInvestorCustomDao;
import com.project.creditmanagementbackend.domain.entity.TbInvestor;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;

@Repository
public class TbInvestorCustomDaoImpl implements TbInvestorCustomDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public String findMaxInvestorCode() {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<String> criteriaQuery = criteriaBuilder.createQuery(String.class);

        Root<TbInvestor> investorEntity = criteriaQuery.from(TbInvestor.class);

        criteriaQuery.select(
                criteriaBuilder.greatest(investorEntity.<String>get("investorCode"))
        );

        return entityManager.createQuery(criteriaQuery).getSingleResult();
    }
}