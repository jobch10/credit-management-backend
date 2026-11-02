package com.project.creditmanagementbackend.app.util;

import com.project.creditmanagementbackend.app.service.TokenBlacklistService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class TokenCleanupTask {

    private final TokenBlacklistService tokenBlacklistService;
    @Scheduled(cron = "0 00 3 * * ?")
    public void cleanExpiredTokens() {
        log.info("Starting cleanup of expired blacklisted tokens...");
        tokenBlacklistService.cleanExpiredTokens();
        log.info("Cleanup of expired blacklisted tokens finished.");
    }
}
