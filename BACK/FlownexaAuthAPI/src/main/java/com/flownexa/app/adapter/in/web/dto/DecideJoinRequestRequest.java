package com.flownexa.app.adapter.in.web.dto;

import com.flownexa.app.domain.enums.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;


@Schema(description = "Decision on a pending join request")
public class DecideJoinRequestRequest {

    @NotNull(message = "Approved flag is required")
    @Schema(description = "true to approve, false to reject")
    private Boolean approved;

    @Schema(description = "Role to assign if approved. Required when approved=true")
    private Role role;

    public Boolean getApproved() { return approved; }
    public void setApproved(Boolean approved) { this.approved = approved; }

    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }
}
