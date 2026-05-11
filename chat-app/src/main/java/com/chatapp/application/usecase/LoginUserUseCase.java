package com.chatapp.application.usecase;

import com.chatapp.application.ports.UserRepositoryPort;
import com.chatapp.domain.valueobject.UserId;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.UUID;

public class LoginUserUseCase {

    private final UserRepositoryPort userRepository;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public LoginUserUseCase(UserRepositoryPort userRepository) {
        this.userRepository = userRepository;
    }

    public String execute(String userId, String password) {

        var user = userRepository.findById(new UserId(userId))
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!encoder.matches(password, user.getPasswordHash())) {
            throw new RuntimeException("Invalid credentials");
        }

        return UUID.randomUUID().toString();
    }
}