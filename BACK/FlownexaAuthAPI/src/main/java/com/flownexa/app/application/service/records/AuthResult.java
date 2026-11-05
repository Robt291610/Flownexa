package com.flownexa.app.application.service.records;

import com.flownexa.app.domain.enums.Role;
import java.util.UUID;

public record AuthResult(UUID userId, String email, UUID organizationId, Role role, String token) {}
