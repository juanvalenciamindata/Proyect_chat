package com.chatapp.interfaces.rest.controller;

import com.chatapp.application.usecase.LoginUserUseCase;
import com.chatapp.interfaces.rest.dto.LoginRequest;
import com.chatapp.infrastructure.security.TokenStore;

import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final LoginUserUseCase loginUserUseCase;
    private final TokenStore tokenStore;

    public AuthController(
            LoginUserUseCase loginUserUseCase,
            TokenStore tokenStore
    ) {
        this.loginUserUseCase = loginUserUseCase;
        this.tokenStore = tokenStore;
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody LoginRequest request) {

        String token = loginUserUseCase.execute(
                request.getUserId(),
                request.getPassword()
        );

        tokenStore.add(token);

        return Map.of("token", token);
    }
}