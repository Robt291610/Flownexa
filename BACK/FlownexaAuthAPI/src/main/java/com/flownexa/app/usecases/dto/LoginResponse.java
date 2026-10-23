package com.flownexa.app.usecases.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

@Schema(description = "Login response payload")
public class LoginResponse {

    @Schema(description = "User ID")
    private UUID userId;

    @Schema(description = "User email")
    private String email;

    @Schema(description = "JWT access token")
    private String accessToken;

    public LoginResponse(UUID userId, String email, String accessToken) {
        this.userId = userId;
        this.email = email;
        this.accessToken = accessToken;
    }

    public UUID getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }

    public String getAccessToken() {
        return accessToken;
    }
}
