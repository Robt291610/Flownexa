package com.flownexa.app.scheduler;

import com.flownexa.app.adapter.out.persistance.PasswordResetTokenRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class PasswordResetTokenCleanupJob {

    private final PasswordResetTokenRepository tokenRepository;

    public PasswordResetTokenCleanupJob(PasswordResetTokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    @Scheduled(cron = "0 0 3 * * *") // every day at 3 AM
    public void purgeExpiredTokens() {
        tokenRepository.deleteAllExpired();
    }
}
