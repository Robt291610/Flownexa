package com.flownexa.business.service;

import com.flownexa.auth.adapter.out.persistance.UsersRepository;
import com.flownexa.auth.config.AuthenticatedUser;
import com.flownexa.auth.domain.enums.Role;
import com.flownexa.auth.domain.enums.UserStatus;
import com.flownexa.auth.domain.exception.InvalidCredentialsException;
import com.flownexa.auth.domain.model.Users;
import com.flownexa.business.application.dto.DashboardSummaryResponse;
import com.flownexa.business.application.dto.DashboardSummaryResponse.AdminStats;
import com.flownexa.business.application.repository.DepartmentRepository;
import java.util.EnumMap;
import java.util.Map;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class DashboardService {

    private final UsersRepository usersRepository;
    private final DepartmentRepository departmentRepository;

    public DashboardService(UsersRepository usersRepository,
                            DepartmentRepository departmentRepository) {
        this.usersRepository = usersRepository;
        this.departmentRepository = departmentRepository;
    }

    @Transactional(readOnly = true)
    public DashboardSummaryResponse getSummary(AuthenticatedUser principal) {
        Users user = usersRepository.findById(principal.getUserId())
                .orElseThrow(() -> new InvalidCredentialsException("User not found"));

        // Defense in depth: the token may outlive a deactivated account
        if (user.getStatus() != UserStatus.ACTIVE) {
            throw new InvalidCredentialsException("Account is not active");
        }

        UUID orgId = user.getOrganization().getId();

        AdminStats adminStats = user.getRole() == Role.ADMIN ? buildAdminStats(orgId) : null;

        return new DashboardSummaryResponse(
                user.getId(),
                user.getFullName(),
                user.getEmail(),
                user.getRole(),
                user.getOrganization().getName(),
                user.getDepartment() != null ? user.getDepartment().getName() : null,
                adminStats
        );
    }

    private AdminStats buildAdminStats(UUID orgId) {
        Map<Role, Long> byRole = new EnumMap<>(Role.class);
        long activeUsers = 0;
        for (Role role : Role.values()) {
            long count = usersRepository.countByOrganizationIdAndRoleAndStatus(orgId, role, UserStatus.ACTIVE);
            byRole.put(role, count);
            activeUsers += count;
        }

        return new AdminStats(
                departmentRepository.countByOrganizationIdAndActiveTrue(orgId),
                activeUsers,
                usersRepository.countByOrganizationIdAndStatus(orgId, UserStatus.PENDING),
                byRole
        );
    }
}
