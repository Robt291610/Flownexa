package com.flownexa.app.usecases.services;

import com.flownexa.app.entities.Users;
import com.flownexa.app.persistance.exeptions.EmailAlreadyExistsException;
import com.flownexa.app.persistance.exeptions.InvalidCredentialsException;
import com.flownexa.app.persistance.repository.UsersRepository;
import com.flownexa.app.usecases.dto.LoginRequest;
import com.flownexa.app.usecases.dto.LoginResponse;
import com.flownexa.app.usecases.dto.RegisterRequest;
import com.flownexa.app.usecases.dto.RegisterResponse;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UsersRepository usersRepository, PasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
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

        // Placeholder token — swap with real JWT generation later
        String token = "dummy-token-" + user.getId();

        return new LoginResponse(user.getId(), user.getEmail(), token);
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
