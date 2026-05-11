package com.chatapp.interfaces.rest.controller;

import com.chatapp.application.ports.UserRepositoryPort;
import com.chatapp.domain.model.User;
import com.chatapp.interfaces.rest.dto.LoginRequest;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserRepositoryPort userRepository;

    // 🔐 encoder password
    private final BCryptPasswordEncoder encoder =
            new BCryptPasswordEncoder();

    public UserController(UserRepositoryPort userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping
    public Map<String, String> createUser(
            @RequestBody LoginRequest request
    ) {

        // 🔐 hash password
        String hash = encoder.encode(request.getPassword());

        // 👤 crear user
        User user = User.create(hash);

        // 💾 guardar en SQLite
        userRepository.save(user);

        return Map.of(
                "userId",
                user.getId().value()
        );
    }
}