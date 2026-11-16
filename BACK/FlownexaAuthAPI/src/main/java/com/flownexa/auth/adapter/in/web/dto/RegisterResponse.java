package com.flownexa.auth.adapter.in.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.UUID;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Schema(description = "Registration response payload")
public class RegisterResponse {

    @Schema(description = "Newly created user ID")
    private UUID userId;

    @Schema(description = "User email")
    private String email;
}
