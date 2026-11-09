package com.flownexa.app.adapter.in.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;



@Schema(description = "Request to create a brand-new company and its first admin user")
public class CreateCompanyRequest {

    // --- Company fields ---

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

    // --- Admin user fields ---

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
    @Schema(example = "Founder")
    private String jobTitle;

    // --- Getters & setters ---

    public String getCompanyName() { return companyName; }
    public void setCompanyName(String companyName) { this.companyName = companyName; }

    public String getLegalName() { return legalName; }
    public void setLegalName(String legalName) { this.legalName = legalName; }

    public String getTaxId() { return taxId; }
    public void setTaxId(String taxId) { this.taxId = taxId; }

    public String getIndustry() { return industry; }
    public void setIndustry(String industry) { this.industry = industry; }

    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }

    public String getCompanyPhone() { return companyPhone; }
    public void setCompanyPhone(String companyPhone) { this.companyPhone = companyPhone; }

    public String getContactEmail() { return contactEmail; }
    public void setContactEmail(String contactEmail) { this.contactEmail = contactEmail; }

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
