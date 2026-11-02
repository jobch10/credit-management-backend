package com.project.creditmanagementbackend.app.dao.custom.impl;

import com.project.creditmanagementbackend.app.dao.custom.TbClientCustomDao;
import com.project.creditmanagementbackend.domain.entity.TbClient;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;

@Repository
public class TbClientCustomDaoImpl implements TbClientCustomDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public String findMaxClientCode() {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<String> criteriaQuery = criteriaBuilder.createQuery(String.class);
        Root<TbClient> clientEntity = criteriaQuery.from(TbClient.class);
        criteriaQuery.select(
                criteriaBuilder.greatest(clientEntity.<String>get("clientCode"))
        );
        return entityManager.createQuery(criteriaQuery).getSingleResult();
    }
}