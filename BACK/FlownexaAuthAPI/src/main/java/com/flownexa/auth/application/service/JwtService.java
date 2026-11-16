package com.flownexa.auth.application.service;

import com.flownexa.auth.domain.enums.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.UUID;

@Component
public class JwtService {

    private final SecretKey signingKey;
    private final long expirationMs;
    private final String issuer;

    public JwtService(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.expiration-ms}") long expirationMs,
            @Value("${jwt.issuer}") String issuer) {
        this.signingKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.expirationMs = expirationMs;
        this.issuer = issuer;
    }

//    public String generateAccessToken(UUID userId, String email) {
//        Date now = new Date();
//        Date expiry = new Date(now.getTime() + expirationMs);
//
//        return Jwts.builder()
//                .issuer(issuer)
//                .subject(userId.toString())
//                .claim("email", email)
//                .issuedAt(now)
//                .expiration(expiry)
//                .signWith(signingKey)
//                .compact();
//    }

    public Claims validateAndParse(String token) {
        return Jwts.parser()
                .verifyWith(signingKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public UUID extractUserId(String token) {
        return UUID.fromString(validateAndParse(token).getSubject());
    }

    public boolean isValid(String token) {
        try {
            validateAndParse(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    public String generateAccessToken(UUID userId, String email, UUID organizationId, Role role) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + expirationMs);

        return Jwts.builder()
                .issuer(issuer)
                .subject(userId.toString())
                .claim("email", email)
                .claim("organizationId", organizationId.toString())
                .claim("role", role.name())
                .issuedAt(now)
                .expiration(expiry)
                .signWith(signingKey)
                .compact();
    }
}
