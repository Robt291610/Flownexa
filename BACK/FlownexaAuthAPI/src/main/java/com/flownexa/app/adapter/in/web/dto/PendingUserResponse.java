package com.flownexa.app.adapter.in.web.dto;

import com.flownexa.app.domain.enums.UserStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.UUID;

@Schema(description = "A pending or decided join request")
public class PendingUserResponse {

    private UUID userId;
    private String email;
    private UserStatus status;
    private LocalDateTime requestedAt;

    public PendingUserResponse(UUID userId, String email, UserStatus status, LocalDateTime requestedAt) {
        this.userId = userId;
        this.email = email;
        this.status = status;
        this.requestedAt = requestedAt;
    }

    public UUID getUserId() { return userId; }
    public String getEmail() { return email; }
    public UserStatus getStatus() { return status; }
    public LocalDateTime getRequestedAt() { return requestedAt; }
}
