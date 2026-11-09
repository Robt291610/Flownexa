package com.flownexa.app.adapter.in.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Schema(description = "Company registration request — creates the organization and its first admin user")
public class RegisterCompanyRequest {

    
    @NotBlank(message = "Company name is required")
    @Size(max = 150)
    @Schema(example = "Acme Corp")
    private String companyName;

    @NotBlank(message = "Legal name is required")
    @Size(max = 200)
    @Schema(example = "Acme Corporation S.A.")
    private String legalName;

    @NotBlank(message = "Tax ID is required")
    @Size(max = 50)
    @Schema(example = "900123456-7")
    private String taxId;

    @Size(max = 100)
    @Schema(example = "Manufacturing")
    private String industry;

    @NotBlank(message = "Country is required")
    @Size(max = 100)
    @Schema(example = "Colombia")
    private String country;

    @Size(max = 30)
    @Schema(example = "+57 300 1234567")
    private String companyPhone;

    @Email(message = "Contact email must be valid")
    @Schema(example = "contact@acme.com")
    private String contactEmail;

    @NotBlank(message = "Full name is required")
    @Size(max = 150)
    @Schema(example = "Jane Doe")
    private String fullName;

    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    @Schema(example = "jane@acme.com")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters")
    @Schema(example = "SecurePass123!")
    private String password;

    @Size(max = 30)
    @Schema(example = "+57 300 7654321")
    private String userPhone;

    @Size(max = 100)
    @Schema(example = "Procurement Analyst")
    private String jobTitle;
}
