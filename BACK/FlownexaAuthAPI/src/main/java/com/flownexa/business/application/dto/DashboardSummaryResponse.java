package com.flownexa.business.application.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.flownexa.auth.domain.enums.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Map;
import java.util.UUID;

@Schema(description = "Role-aware dashboard summary")
@JsonInclude(JsonInclude.Include.NON_NULL)
public record DashboardSummaryResponse(
        UUID userId,
        String fullName,
        String email,
        Role role,
        String organizationName,
        String departmentName,
        AdminStats adminStats
) {

    @Schema(description = "Organization-wide stats. Only present for ADMIN users")
    public record AdminStats(
            long activeDepartments,
            long activeUsers,
            long pendingJoinRequests,
            Map<Role, Long> activeUsersByRole
    ) {}
}
