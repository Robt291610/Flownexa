package com.flownexa.auth.adapter.in.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.UUID;

@Schema(description = "Current authenticated user")
public class MeResponse {
  private final UUID userId;
    private final String email;

    public MeResponse(UUID userId, String email) {
        this.userId = userId;
        this.email = email;
    }

    public UUID getUserId() { return userId; }
    public String getEmail() { return email; }
}
