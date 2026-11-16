package com.flownexa.auth.application.service.records;

import com.flownexa.auth.domain.enums.Role;
import java.util.UUID;

public record AuthResult(UUID userId, String email, UUID organizationId, Role role, String token) {}
