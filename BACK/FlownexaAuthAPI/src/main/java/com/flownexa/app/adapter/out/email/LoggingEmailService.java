package com.flownexa.app.adapter.out.email;

import com.flownexa.app.adapter.out.email.interfaces.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("local")
public class LoggingEmailService implements EmailService {

    private static final Logger log = LoggerFactory.getLogger(LoggingEmailService.class);

    @Override
    public void sendPasswordResetEmail(String to, String resetLink) {
        log.info("=== PASSWORD RESET EMAIL (local mode) ===");
        log.info("To: {}", to);
        log.info("Link: {}", resetLink);
        log.info("==========================================");
    }
    
    @Override
    public void sendJoinRequestNotification(String adminEmail, String requesterEmail, String companyName) {
        log.info("=== JOIN REQUEST NOTIFICATION (local mode) ===");
        log.info("To admin: {}", adminEmail);
        log.info("Requester: {} wants to join {}", requesterEmail, companyName);
        log.info("================================================");
    }
}