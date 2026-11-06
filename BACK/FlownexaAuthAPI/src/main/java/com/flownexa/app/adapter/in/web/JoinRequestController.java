package com.flownexa.app.adapter.in.web;

import com.flownexa.app.adapter.in.web.dto.DecideJoinRequestRequest;
import com.flownexa.app.adapter.in.web.dto.PendingUserResponse;
import com.flownexa.app.application.service.JoinRequestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/admin/join-requests")
@Tag(name = "Join Requests", description = "Admin endpoints to review pending company join requests")
public class JoinRequestController {

    private final JoinRequestService joinRequestService;

    public JoinRequestController(JoinRequestService joinRequestService) {
        this.joinRequestService = joinRequestService;
    }

    @GetMapping
    @Operation(summary = "List pending join requests for the admin's organization")
    public ResponseEntity<List<PendingUserResponse>> listPending(Authentication authentication) {
        UUID adminId = currentUserId(authentication);
        return ResponseEntity.ok(joinRequestService.listPending(adminId));
    }

    @PostMapping("/{userId}/decision")
    @Operation(summary = "Approve or reject a pending join request")
    public ResponseEntity<PendingUserResponse> decide(
            Authentication authentication,
            @PathVariable UUID userId,
            @Valid @RequestBody DecideJoinRequestRequest request) {
        UUID adminId = currentUserId(authentication);
        return ResponseEntity.ok(joinRequestService.decide(adminId, userId, request));
    }

    private UUID currentUserId(Authentication authentication) {
        return UUID.fromString((String) authentication.getPrincipal());
    }
}
