package com.project.creditmanagementbackend.app.service;

import java.time.LocalDateTime;

public interface TokenBlacklistService {
    void blacklistToken(String token, String username, String reason, LocalDateTime expiryDate);

    boolean isTokenBlacklisted(String token);

    void cleanExpiredTokens();
}
