package com.flownexa.app.application.service;

import com.flownexa.app.domain.model.Users;
import com.flownexa.app.domain.exception.EmailAlreadyExistsException;
import com.flownexa.app.domain.exception.InvalidCredentialsException;
import com.flownexa.app.adapter.out.persistance.UsersRepository;
import com.flownexa.app.adapter.in.web.dto.LoginRequest;
import com.flownexa.app.adapter.in.web.dto.LoginResponse;
import com.flownexa.app.adapter.in.web.dto.RegisterRequest;
import com.flownexa.app.adapter.in.web.dto.RegisterResponse;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(UsersRepository usersRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public LoginResponse login(LoginRequest request) {
        Users user = usersRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new InvalidCredentialsException("Invalid email or password"));

        if (!user.isActive()) {
            throw new InvalidCredentialsException("Account is inactive");
        }

        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            throw new InvalidCredentialsException("Invalid email or password");
        }

        String accessToken = jwtService.generateAccessToken(user.getId(), user.getEmail());

        return new LoginResponse(user.getId(), user.getEmail(), accessToken);
    }
    
    public RegisterResponse register(RegisterRequest request) {
        usersRepository.findByEmail(request.getEmail())
                .ifPresent(u -> {
                    throw new EmailAlreadyExistsException("Email is already registered");
                });

        Users user = new Users();
        user.setEmail(request.getEmail());
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        user.setActive(true);

        Users saved = usersRepository.save(user);

        return new RegisterResponse(saved.getId(), saved.getEmail());
    }
    
}
