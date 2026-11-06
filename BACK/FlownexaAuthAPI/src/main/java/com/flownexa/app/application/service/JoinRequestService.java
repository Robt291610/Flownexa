package com.flownexa.app.application.service;

import com.flownexa.app.adapter.in.web.dto.DecideJoinRequestRequest;
import com.flownexa.app.adapter.in.web.dto.PendingUserResponse;
import com.flownexa.app.adapter.out.persistance.UsersRepository;
import com.flownexa.app.domain.enums.Role;
import com.flownexa.app.domain.enums.UserStatus;
import com.flownexa.app.domain.exception.InvalidCredentialsException;
import com.flownexa.app.domain.exception.UnauthorizedActionException;
import com.flownexa.app.domain.model.Users;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class JoinRequestService {

    private final UsersRepository usersRepository;

    public JoinRequestService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public List<PendingUserResponse> listPending(UUID adminUserId) {
        Users admin = requireAdmin(adminUserId);

        return usersRepository.findByOrganizationAndStatus(admin.getOrganization(), UserStatus.PENDING)
                .stream()
                .map(u -> new PendingUserResponse(u.getId(), u.getEmail(), u.getStatus(), u.getRequestedAt()))
                .toList();
    }

    @Transactional
    public PendingUserResponse decide(UUID adminUserId, UUID targetUserId, DecideJoinRequestRequest request) {
        Users admin = requireAdmin(adminUserId);

        Users target = usersRepository.findById(targetUserId)
                .orElseThrow(() -> new InvalidCredentialsException("User not found"));

        if (!target.getOrganization().getId().equals(admin.getOrganization().getId())) {
            throw new UnauthorizedActionException("Cannot decide on a request from a different organization");
        }

        if (target.getStatus() != UserStatus.PENDING) {
            throw new UnauthorizedActionException("This request has already been decided");
        }

        if (Boolean.TRUE.equals(request.getApproved())) {
            if (request.getRole() == null) {
                throw new UnauthorizedActionException("Role is required to approve a request");
            }
            target.setRole(request.getRole());
            target.setStatus(UserStatus.ACTIVE);
        } else {
            target.setStatus(UserStatus.REJECTED);
        }

        target.setDecidedAt(LocalDateTime.now());
        target.setDecidedBy(admin);
        Users saved = usersRepository.save(target);

        return new PendingUserResponse(saved.getId(), saved.getEmail(), saved.getStatus(), saved.getRequestedAt());
    }

    private Users requireAdmin(UUID userId) {
        Users user = usersRepository.findById(userId)
                .orElseThrow(() -> new InvalidCredentialsException("User not found"));

        if (user.getRole() != Role.ADMIN || user.getStatus() != UserStatus.ACTIVE) {
            throw new UnauthorizedActionException("Only active admins can perform this action");
        }

        return user;
    }
}
