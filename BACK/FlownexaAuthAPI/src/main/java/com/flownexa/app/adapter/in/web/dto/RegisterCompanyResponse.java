package com.flownexa.app.adapter.in.web.dto;

import com.flownexa.app.domain.enums.UserStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.UUID;
import lombok.*;

@Getter
@AllArgsConstructor
@Schema(description = "Company registration response")
public class RegisterCompanyResponse {

    @Schema(description = "Organization ID")
    private UUID organizationId;

    @Schema(description = "Organization name")
    private String companyName;

    @Schema(description = "User ID created or requested")
    private UUID UserId;

    @Schema(description = "Email of the requester")
    private String email;
    
    @Schema(description = "ACTIVE if a new company+admin was created, PENDING if awaiting approval from existing admins")
    private UserStatus status;
}
