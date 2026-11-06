package com.flownexa.app.adapter.out.email.interfaces;

public interface EmailService {
    void sendPasswordResetEmail(String to, String resetLink);
    
    void sendJoinRequestNotification(String adminEmail, String requesterEmail, String companyName);
}