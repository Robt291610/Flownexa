package com.flownexa.app.adapter.in.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

@Schema(description = "Registration response payload")
public class RegisterResponse {

    @Schema(description = "Newly created user ID")
    private UUID userId;

    @Schema(description = "User email")
    private String email;

    public RegisterResponse(UUID userId, String email) {
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
