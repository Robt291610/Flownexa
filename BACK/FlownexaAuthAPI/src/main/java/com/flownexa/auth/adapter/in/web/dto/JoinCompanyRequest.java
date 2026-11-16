package com.flownexa.auth.adapter.in.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Request to join an existing company; creates a pending user awaiting admin approval")
public class JoinCompanyRequest {

    @NotBlank(message = "Company name is required")
    @Schema(example = "Acme Corp")
    private String companyName;

    @NotBlank(message = "Full name is required")
    @Size(max = 150)
    @Schema(example = "John Smith")
    private String fullName;

    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    @Schema(example = "john@acme.com")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters")
    @Schema(example = "SecurePass123!")
    private String password;

    @Size(max = 30)
    @Schema(example = "+57 300 9998888")
    private String userPhone;

    @Size(max = 100)
    @Schema(example = "Procurement Analyst")
    private String jobTitle;

    public String getCompanyName() { return companyName; }
    public void setCompanyName(String companyName) { this.companyName = companyName; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getUserPhone() { return userPhone; }
    public void setUserPhone(String userPhone) { this.userPhone = userPhone; }

    public String getJobTitle() { return jobTitle; }
    public void setJobTitle(String jobTitle) { this.jobTitle = jobTitle; }
}
