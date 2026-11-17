package com.flownexa.auth.adapter.in.web.dto;

import com.flownexa.auth.domain.enums.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.UUID;

@Schema(description = "Current authenticated user")
public class MeResponse {
    private final UUID userId;
    private final String email;
    private Role role;
    private UUID organizationId;

    public MeResponse(UUID userId, String email, Role role, UUID organizationId) {
        this.userId = userId;
        this.email = email;
        this.role = role;
        this.organizationId = organizationId;
    }

    public UUID getUserId() { return userId; }
    public String getEmail() { return email; }
    public Role getRole() { return role; }
    public UUID getOrganizationId() { return organizationId; }
}
