package com.project.creditmanagementbackend.app.dao;

import com.project.creditmanagementbackend.domain.entity.TbTokenBlacklist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface TbTokenBlacklistDao extends JpaRepository<TbTokenBlacklist, Long> {

    Optional<TbTokenBlacklist> findByToken(String token);

    boolean existsByToken(String token);

    void deleteByExpiryDateBefore(LocalDateTime dateTime);
}
