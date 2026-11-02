package com.project.creditmanagementbackend.app.dao;

import com.project.creditmanagementbackend.domain.entity.TbClient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.project.creditmanagementbackend.app.dao.custom.TbClientCustomDao;
import java.util.Optional;

@Repository
public interface TbClientDao extends JpaRepository<TbClient, Integer>, JpaSpecificationExecutor<TbClient>, TbClientCustomDao {
    Optional<TbClient> findByClientCode(String clientCode);
}
