package com.chatapp.infrastructure.config;

import com.chatapp.application.ports.*;
import com.chatapp.application.usecase.*;
import com.chatapp.infrastructure.kafka.KafkaMessagePublisher;
import com.chatapp.infrastructure.persistence.sqlite.SqliteUserRepository;
import com.chatapp.infrastructure.security.TokenStore;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;

@Configuration
public class AppConfig {

    // MESSAGE USE CASE
    @Bean
    public SendMessageUseCase sendMessageUseCase(
            MessageRepositoryPort repository,
            MessageEventPublisherPort publisher
    ) {
        return new SendMessageService(repository, publisher);
    }

    // LOGIN USE CASE
    @Bean
    public LoginUserUseCase loginUserUseCase(
            UserRepositoryPort userRepository
    ) {
        return new LoginUserUseCase(userRepository);
    }

    // HISTORY
    @Bean
    public GetMessageHistoryUseCase getMessageHistoryUseCase(
            MessageRepositoryPort repository
    ) {
        return new GetMessageHistoryUseCase(repository);
    }

    // BY USER
    @Bean
    public GetMessagesByUserUseCase getMessagesByUserUseCase(
            MessageRepositoryPort repository
    ) {
        return new GetMessagesByUserUseCase(repository);
    }

    // USER REPOSITORY
    @Bean
    public UserRepositoryPort userRepositoryPort() {
        return new SqliteUserRepository();
    }

    // TOKEN STORE
    @Bean
    public TokenStore tokenStore() {
        return new TokenStore();
    }

    // KAFKA
    @Bean
    public MessageEventPublisherPort messageEventPublisherPort(
            KafkaTemplate<String, String> kafkaTemplate,
            ObjectMapper objectMapper
    ) {
        return new KafkaMessagePublisher(kafkaTemplate, objectMapper);
    }

    // JSON
    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}