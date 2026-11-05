package com.flownexa.app.adapter.in.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Company registration request — creates the organization and its first admin user")
public class RegisterCompanyRequest {

    @NotBlank(message = "Company name is required")
    @Size(max = 150)
    @Schema(example = "Acme Corp")
    private String companyName;

    @NotBlank(message = "Admin email is required")
    @Email(message = "Email must be valid")
    @Schema(example = "admin@acme.com")
    private String adminEmail;

    @NotBlank(message = "Admin password is required")
    @Size(min = 8, message = "Password must be at least 8 characters")
    @Schema(example = "SecurePass123!")
    private String adminPassword;

    public String getCompanyName() { return companyName; }
    public void setCompanyName(String companyName) { this.companyName = companyName; }

    public String getAdminEmail() { return adminEmail; }
    public void setAdminEmail(String adminEmail) { this.adminEmail = adminEmail; }

    public String getAdminPassword() { return adminPassword; }
    public void setAdminPassword(String adminPassword) { this.adminPassword = adminPassword; }
}
