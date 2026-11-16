package com.flownexa.auth.adapter.out.email;

import com.flownexa.auth.adapter.out.email.interfaces.EmailService;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.mail.javamail.JavaMailSender;



@Service
@Profile("!local")
public class SmtpEmailService implements EmailService {

    private final JavaMailSender mailSender;

    public SmtpEmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendPasswordResetEmail(String to, String resetLink) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Reset your password");
        message.setText("""
                We received a request to reset your password.
                Click the link below to set a new one. It expires in 15 minutes.

                %s

                If you did not request this, you can ignore this email.
                """.formatted(resetLink));
        mailSender.send(message);
    }
    
    @Override
    public void sendJoinRequestNotification(String adminEmail, String requesterEmail, String companyName) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(adminEmail);
        message.setSubject("New request to join " + companyName);
        message.setText("""
                %s has requested to join %s.

                Review and approve or reject this request in the admin panel.
                """.formatted(requesterEmail, companyName));
        mailSender.send(message);
    }
}
