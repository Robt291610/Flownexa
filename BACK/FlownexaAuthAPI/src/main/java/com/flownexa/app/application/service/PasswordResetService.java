package com.flownexa.app.application.service;

import com.flownexa.app.domain.model.PasswordResetToken;
import com.flownexa.app.domain.model.Users;
import com.flownexa.app.domain.exception.InvalidResetTokenException;
import com.flownexa.app.adapter.out.persistance.PasswordResetTokenRepository;
import com.flownexa.app.adapter.out.persistance.UsersRepository;
import com.flownexa.app.adapter.in.web.dto.ForgotPasswordRequest;
import com.flownexa.app.adapter.in.web.dto.ResetPasswordRequest;
import com.flownexa.app.adapter.out.email.interfaces.EmailService;
import jakarta.transaction.Transactional;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.HexFormat;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PasswordResetService {

    private static final Logger log = LoggerFactory.getLogger(PasswordResetService.class);
    private static final SecureRandom RANDOM = new SecureRandom();

    private final UsersRepository usersRepository;
    private final PasswordResetTokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final int expirationMinutes;
    private final String resetUrl;

    public PasswordResetService(UsersRepository usersRepository,
                                 PasswordResetTokenRepository tokenRepository,
                                 PasswordEncoder passwordEncoder,
                                 EmailService emailService,
                                 @Value("${app.password-reset.expiration-minutes}") int expirationMinutes,
                                 @Value("${app.password-reset.url}") String resetUrl) {
        this.usersRepository = usersRepository;
        this.tokenRepository = tokenRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
        this.expirationMinutes = expirationMinutes;
        this.resetUrl = resetUrl;
    }

    @Transactional
    public void forgotPassword(ForgotPasswordRequest request) {
        Optional<Users> userOpt = usersRepository.findByEmail(request.getEmail());

        // Always behave the same way whether the email exists or not,
        // so callers can't use this endpoint to discover registered emails.
        if (userOpt.isEmpty()) {
            log.info("Password reset requested for unknown email: {}", request.getEmail());
            return;
        }

        Users user = userOpt.get();

        // Remove any previous outstanding tokens for this user first.
        tokenRepository.deleteByUser(user);

        String rawToken = generateRawToken();
        String hashedToken = hashToken(rawToken);

        PasswordResetToken resetToken = new PasswordResetToken();
        resetToken.setUser(user);
        resetToken.setTokenHash(hashedToken);
        resetToken.setExpiresAt(LocalDateTime.now().plusMinutes(expirationMinutes));
        resetToken.setUsed(false);

        tokenRepository.save(resetToken);

        String link = resetUrl + "?token=" + rawToken;
        emailService.sendPasswordResetEmail(user.getEmail(), link);
    }

    @Transactional
    public void resetPassword(ResetPasswordRequest request) {
        String hashedToken = hashToken(request.getToken());

        PasswordResetToken resetToken = tokenRepository.findByTokenHash(hashedToken)
                .orElseThrow(() -> new InvalidResetTokenException("Invalid or expired token"));

        if (resetToken.isUsed()) {
            throw new InvalidResetTokenException("This reset link has already been used");
        }

        if (resetToken.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new InvalidResetTokenException("This reset link has expired");
        }

        Users user = resetToken.getUser();
        user.setPasswordHash(passwordEncoder.encode(request.getNewPassword()));
        usersRepository.save(user);

        resetToken.setUsed(true);
        tokenRepository.save(resetToken);
    }

    private String generateRawToken() {
        byte[] bytes = new byte[32];
        RANDOM.nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }

    private String hashToken(String rawToken) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(rawToken.getBytes(StandardCharsets.UTF_8));
            return HexFormat.of().formatHex(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-256 not available", e);
        }
    }
}
