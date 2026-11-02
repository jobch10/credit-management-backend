package com.project.creditmanagementbackend.app.dao;

import com.project.creditmanagementbackend.domain.entity.TbUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TbUserDao extends JpaRepository<TbUser, Integer>, JpaSpecificationExecutor<TbUser> {

    Optional<TbUser> findByUsername(String username);

    Optional<TbUser> findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    boolean existsByEmailAndIdNot(String email, Integer id);

    boolean existsByUsernameAndIdNot(String username, Integer id);
}