package com.flownexa.app.adapter.in.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

@Schema(description = "Login response payload")
public class LoginResponse {

    @Schema(description = "User ID")
    private UUID userId;

    @Schema(description = "User email")
    private String email;

    public LoginResponse(UUID userId, String email) {
        this.userId = userId;
        this.email = email;
    }

    public UUID getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }
}