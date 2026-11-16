package com.flownexa.auth.config;

import com.flownexa.auth.domain.enums.Role;
import java.util.UUID;


public class AuthenticatedUser {

    private final UUID userId;
    private final String email;
    private final UUID organizationId;
    private final Role role;

    public AuthenticatedUser(UUID userId, String email, UUID organizationId, Role role) {
        this.userId = userId;
        this.email = email;
        this.organizationId = organizationId;
        this.role = role;
    }

    public UUID getUserId() { return userId; }
    public String getEmail() { return email; }
    public UUID getOrganizationId() { return organizationId; }
    public Role getRole() { return role; }
}
