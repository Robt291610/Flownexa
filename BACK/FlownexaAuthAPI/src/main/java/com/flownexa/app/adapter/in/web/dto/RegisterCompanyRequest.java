package com.flownexa.app.adapter.in.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Schema(description = "Company registration request — creates the organization and its first admin user")
public class RegisterCompanyRequest {

    @NotBlank(message = "Company name is required")
    @Size(max = 150)
    @Schema(example = "Acme Corp")
    private String companyName;

    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    @Schema(example = "user@acme.com")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters")
    @Schema(example = "SecurePass123!")
    private String password;
}
