package com.flownexa.app.adapter.in.web;

import com.flownexa.app.adapter.in.web.dto.ForgotPasswordRequest;
import com.flownexa.app.adapter.in.web.dto.LoginRequest;
import com.flownexa.app.adapter.in.web.dto.LoginResponse;
import com.flownexa.app.adapter.in.web.dto.MeResponse;
import com.flownexa.app.adapter.in.web.dto.RegisterCompanyRequest;
import com.flownexa.app.adapter.in.web.dto.RegisterCompanyResponse;
import com.flownexa.app.adapter.in.web.dto.ResetPasswordRequest;
import com.flownexa.app.application.service.AuthService;
import com.flownexa.app.application.service.CompanyRegistrationService;
import com.flownexa.app.application.service.CookieService;
import com.flownexa.app.application.service.PasswordResetService;
import com.flownexa.app.application.service.records.AuthResult;
import com.flownexa.app.domain.model.Users;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication", description = "Login and authentication endpoints")
public class AuthController {

    private final AuthService authService;
    private final PasswordResetService passwordResetService;
    private final CookieService cookieService;
    private final CompanyRegistrationService companyRegistrationService;

    public AuthController(AuthService authService,
            PasswordResetService passwordResetService,
            CookieService cookieService,
            CompanyRegistrationService companyRegistrationService) {
        this.authService = authService;
        this.passwordResetService = passwordResetService;
        this.cookieService = cookieService;
        this.companyRegistrationService = companyRegistrationService;
    }

    @PostMapping("/login")
    @Operation(summary = "User login", description = "Authenticates a user by email and password, returns an access token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login successful",
                    content = @Content(schema = @Schema(implementation = LoginResponse.class))),
            @ApiResponse(responseCode = "401", description = "Invalid credentials or inactive account"),
            @ApiResponse(responseCode = "400", description = "Validation error")
    })
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request,
                                                HttpServletResponse response) {
        AuthResult result = authService.loginAndIssueToken(request);
        cookieService.addTokenCookie(response, result.token());
        return ResponseEntity.ok(new LoginResponse(result.userId(), result.email()));
    }
    
    @PostMapping("/register-company")
    @Operation(summary = "Register a new company",
            description = "Creates a new organization and its first admin user")
    public ResponseEntity<RegisterCompanyResponse> registerCompany(@Valid @RequestBody RegisterCompanyRequest request) {
        RegisterCompanyResponse response = companyRegistrationService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    @PostMapping("/forgot-password")
    @Operation(summary = "Request a password reset",
            description = "Sends a password reset link to the user's email if the account exists. Always returns 202.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Request accepted"),
            @ApiResponse(responseCode = "400", description = "Validation error")
    })
    public ResponseEntity<Void> forgotPassword(@Valid @RequestBody ForgotPasswordRequest request) {
        passwordResetService.forgotPassword(request);
        return ResponseEntity.accepted().build();
    }

    @PostMapping("/reset-password")
    @Operation(summary = "Reset password",
            description = "Sets a new password using a valid, unexpired reset token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Password reset successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid, used, or expired token, or validation error")
    })
    public ResponseEntity<Void> resetPassword(@Valid @RequestBody ResetPasswordRequest request) {
        passwordResetService.resetPassword(request);
        return ResponseEntity.ok().build();
    }
    
    @PostMapping("/logout")
    @Operation(summary = "Logout", description = "Clears the session cookie")
    public ResponseEntity<Void> logout(HttpServletResponse response) {
        cookieService.clearTokenCookie(response);
        return ResponseEntity.ok().build();
    }
    
    @GetMapping("/me")
    @Operation(summary = "Current user", description = "Returns the currently authenticated user based on the session cookie")
    public ResponseEntity<MeResponse> me(Authentication authentication) {
        if (authentication == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        UUID userId = UUID.fromString((String) authentication.getPrincipal());
        Users user = authService.findById(userId);
        return ResponseEntity.ok(new MeResponse(user.getId(), user.getEmail()));
    }

}