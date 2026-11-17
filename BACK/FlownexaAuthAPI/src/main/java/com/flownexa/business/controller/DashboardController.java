package com.flownexa.business.controller;

import com.flownexa.auth.config.AuthenticatedUser;
import com.flownexa.business.application.dto.DashboardSummaryResponse;
import com.flownexa.business.service.DashboardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/business/dashboard")
@Tag(name = "Dashboard", description = "Role-aware dashboard data")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/summary")
    @Operation(summary = "Dashboard summary for the current user",
            description = "Any authenticated user gets profile and organization info. Admins also get organization-wide stats.")
    public ResponseEntity<DashboardSummaryResponse> summary(@AuthenticationPrincipal AuthenticatedUser user) {
        return ResponseEntity.ok(dashboardService.getSummary(user));
    }
}
