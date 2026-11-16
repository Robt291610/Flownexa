package com.flownexa.business.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.UUID;


@Schema(description = "Department details")
public class DepartmentResponse {

    private UUID id;
    private String name;
    private String description;
    private boolean active;
    private LocalDateTime createdAt;

    public DepartmentResponse(UUID id, String name, String description, boolean active, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.active = active;
        this.createdAt = createdAt;
    }

    public UUID getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public boolean isActive() { return active; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}
