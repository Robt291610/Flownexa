package com.flownexa.business.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


@Schema(description = "Request to create or update a department")
public class DepartmentRequest {

    @NotBlank(message = "Department name is required")
    @Size(max = 150)
    @Schema(example = "Finance")
    private String name;

    @Size(max = 500)
    @Schema(example = "Handles budgeting and expense approvals")
    private String description;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
