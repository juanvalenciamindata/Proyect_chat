package com.chatapp.infrastructure.config;

import com.chatapp.application.ports.MessageEventPublisherPort;
import com.chatapp.application.ports.MessageRepositoryPort;
import com.chatapp.application.ports.UserRepositoryPort;

import com.chatapp.application.usecase.GetMessageHistoryUseCase;
import com.chatapp.application.usecase.GetMessagesByUserUseCase;
import com.chatapp.application.usecase.LoginUserUseCase;
import com.chatapp.application.usecase.SendMessageService;
import com.chatapp.application.usecase.SendMessageUseCase;

import com.chatapp.infrastructure.kafka.KafkaMessagePublisher;
import com.chatapp.infrastructure.persistence.sqlite.SqliteUserRepository;
import com.chatapp.infrastructure.security.TokenStore;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;

@Configuration
public class AppConfig {

    // 🔥 SEND MESSAGE
    @Bean
    public SendMessageUseCase sendMessageUseCase(
            MessageRepositoryPort repository,
            MessageEventPublisherPort publisher,
            UserRepositoryPort userRepository
    ) {
        return new SendMessageService(
                repository,
                publisher,
                userRepository
        );
    }

    // 🔥 LOGIN
    @Bean
    public LoginUserUseCase loginUserUseCase(
            UserRepositoryPort userRepository
    ) {
        return new LoginUserUseCase(userRepository);
    }

    // 🔥 HISTORY
    @Bean
    public GetMessageHistoryUseCase getMessageHistoryUseCase(
            MessageRepositoryPort repository
    ) {
        return new GetMessageHistoryUseCase(repository);
    }

    // 🔥 MESSAGES BY USER
    @Bean
    public GetMessagesByUserUseCase getMessagesByUserUseCase(
            MessageRepositoryPort repository
    ) {
        return new GetMessagesByUserUseCase(repository);
    }

    // 🔥 USER REPOSITORY (SQLite)
    @Bean
    public UserRepositoryPort userRepositoryPort() {
        return new SqliteUserRepository();
    }

    // 🔥 TOKEN STORE
    @Bean
    public TokenStore tokenStore() {
        return new TokenStore();
    }

    // 🔥 OBJECT MAPPER
    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}