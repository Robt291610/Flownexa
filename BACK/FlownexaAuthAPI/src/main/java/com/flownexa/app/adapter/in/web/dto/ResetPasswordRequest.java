package com.flownexa.app.adapter.in.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Schema(description = "Request to set a new password using a reset token")
public class ResetPasswordRequest {

    @NotBlank(message = "Token is required")
    @Schema(description = "Token from the reset email")
    private String token;

    @NotBlank(message = "New password is required")
    @Size(min = 8, message = "Password must be at least 8 characters")
    @Schema(example = "NewSecurePass123!")
    private String newPassword;
}
