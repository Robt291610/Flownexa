package com.flownexa.auth.config;

import com.flownexa.auth.application.service.CookieService;
import com.flownexa.auth.application.service.JwtService;
import com.flownexa.auth.domain.enums.Role;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final CookieService cookieService;

    public JwtAuthenticationFilter(JwtService jwtService, CookieService cookieService) {
        this.jwtService = jwtService;
        this.cookieService = cookieService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                     HttpServletResponse response,
                                     FilterChain filterChain) throws ServletException, IOException {

        String path = request.getServletPath();

        // Skip auth entirely for public endpoints and docs
        if (path.startsWith("/api/auth/login")
                || path.startsWith("/api/auth/register")
                || path.startsWith("/api/auth/forgot-password")
                || path.startsWith("/api/auth/reset-password")
                || path.startsWith("/v3/api-docs")
                || path.startsWith("/swagger-ui")
                || path.startsWith("/error")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = cookieService.extractToken(request);

        if (token != null && jwtService.isValid(token)) {
            Claims claims = jwtService.validateAndParse(token);

            UUID userId = UUID.fromString(claims.getSubject());
            String email = claims.get("email", String.class);
            UUID organizationId = UUID.fromString(claims.get("organizationId", String.class));
            Role role = Role.valueOf(claims.get("role", String.class));

            AuthenticatedUser principal = new AuthenticatedUser(userId, email, organizationId, role);

            var authentication = new UsernamePasswordAuthenticationToken(
                    principal, null, List.of(new SimpleGrantedAuthority("ROLE_" + role.name())));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }
}
