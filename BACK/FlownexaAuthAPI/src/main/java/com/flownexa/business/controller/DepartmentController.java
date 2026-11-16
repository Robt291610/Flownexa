package com.flownexa.business.controller;

import com.flownexa.auth.config.AuthenticatedUser;
import com.flownexa.business.application.dto.DepartmentRequest;
import com.flownexa.business.application.dto.DepartmentResponse;
import com.flownexa.business.service.DepartmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/business/departments")
@Tag(name = "Departments", description = "Department management, scoped to the user's organization")
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping
    @Operation(summary = "List departments of the user's organization")
    public ResponseEntity<List<DepartmentResponse>> list(@AuthenticationPrincipal AuthenticatedUser user) {
        return ResponseEntity.ok(departmentService.list(user));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Create a department (admin only)")
    public ResponseEntity<DepartmentResponse> create(@AuthenticationPrincipal AuthenticatedUser admin,
                                                     @Valid @RequestBody DepartmentRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(departmentService.create(admin, request));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update a department (admin only)")
    public ResponseEntity<DepartmentResponse> update(@AuthenticationPrincipal AuthenticatedUser admin,
                                                     @PathVariable UUID id,
                                                     @Valid @RequestBody DepartmentRequest request) {
        return ResponseEntity.ok(departmentService.update(admin, id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Deactivate a department (admin only)")
    public ResponseEntity<Void> deactivate(@AuthenticationPrincipal AuthenticatedUser admin,
                                           @PathVariable UUID id) {
        departmentService.deactivate(admin, id);
        return ResponseEntity.noContent().build();
    }
}