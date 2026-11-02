package com.project.creditmanagementbackend.app.service.impl;

import com.project.creditmanagementbackend.app.dao.TbTokenBlacklistDao;
import com.project.creditmanagementbackend.domain.entity.TbTokenBlacklist;
import com.project.creditmanagementbackend.app.service.TokenBlacklistService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TokenBlacklistServiceImpl implements TokenBlacklistService {

    private final TbTokenBlacklistDao tokenBlacklistDao;

    @Override
    @Transactional
    public void blacklistToken(String token, String username, String reason, LocalDateTime expiryDate) {
        if (!tokenBlacklistDao.existsByToken(token)) {
            TbTokenBlacklist blacklistedToken = TbTokenBlacklist.builder()
                    .token(token)
                    .username(username)
                    .reason(reason)
                    .expiryDate(expiryDate)
                    .build();
            tokenBlacklistDao.save(blacklistedToken);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isTokenBlacklisted(String token) {
        return tokenBlacklistDao.existsByToken(token);
    }

    @Override
    @Transactional
    public void cleanExpiredTokens() {
        tokenBlacklistDao.deleteByExpiryDateBefore(LocalDateTime.now());
    }
}
