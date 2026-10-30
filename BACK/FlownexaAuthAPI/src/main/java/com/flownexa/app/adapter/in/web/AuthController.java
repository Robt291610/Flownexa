package com.flownexa.app.adapter.in.web;

import com.flownexa.app.adapter.in.web.dto.ForgotPasswordRequest;
import com.flownexa.app.adapter.in.web.dto.LoginRequest;
import com.flownexa.app.adapter.in.web.dto.LoginResponse;
import com.flownexa.app.adapter.in.web.dto.RegisterRequest;
import com.flownexa.app.adapter.in.web.dto.RegisterResponse;
import com.flownexa.app.adapter.in.web.dto.ResetPasswordRequest;
import com.flownexa.app.application.service.AuthService;
import com.flownexa.app.application.service.CookieService;
import com.flownexa.app.application.service.PasswordResetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication", description = "Login and authentication endpoints")
public class AuthController {

    private final AuthService authService;
    private final PasswordResetService passwordResetService;
    private final CookieService cookieService;

    public AuthController(AuthService authService,
            PasswordResetService passwordResetService,
            CookieService cookieService) {
        this.authService = authService;
        this.passwordResetService = passwordResetService;
        this.cookieService = cookieService;
    }

    @PostMapping("/login")
    @Operation(summary = "User login", description = "Authenticates a user by email and password, returns an access token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login successful",
                    content = @Content(schema = @Schema(implementation = LoginResponse.class))),
            @ApiResponse(responseCode = "401", description = "Invalid credentials or inactive account"),
            @ApiResponse(responseCode = "400", description = "Validation error")
    })
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        LoginResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/register")
    @Operation(summary = "Register new user", description = "Creates a new user account with email and password")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User registered successfully",
                    content = @Content(schema = @Schema(implementation = RegisterResponse.class))),
            @ApiResponse(responseCode = "409", description = "Email already registered"),
            @ApiResponse(responseCode = "400", description = "Validation error")
    })
    public ResponseEntity<RegisterResponse> register(@Valid @RequestBody RegisterRequest request) {
        RegisterResponse response = authService.register(request);
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
    
}