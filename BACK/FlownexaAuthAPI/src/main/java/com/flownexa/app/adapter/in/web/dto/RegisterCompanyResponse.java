package com.flownexa.app.adapter.in.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.UUID;

@Schema(description = "Company registration response")
public class RegisterCompanyResponse {

    @Schema(description = "Organization ID")
    private UUID organizationId;

    @Schema(description = "Organization name")
    private String companyName;

    @Schema(description = "Admin user ID")
    private UUID adminUserId;

    @Schema(description = "Admin email")
    private String adminEmail;

    public RegisterCompanyResponse(UUID organizationId, String companyName, UUID adminUserId, String adminEmail) {
        this.organizationId = organizationId;
        this.companyName = companyName;
        this.adminUserId = adminUserId;
        this.adminEmail = adminEmail;
    }

    public UUID getOrganizationId() { return organizationId; }
    public String getCompanyName() { return companyName; }
    public UUID getAdminUserId() { return adminUserId; }
    public String getAdminEmail() { return adminEmail; }
}
