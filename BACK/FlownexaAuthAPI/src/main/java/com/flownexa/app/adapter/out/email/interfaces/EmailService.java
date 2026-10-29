package com.flownexa.app.adapter.out.email.interfaces;

public interface EmailService {
    void sendPasswordResetEmail(String to, String resetLink);
}