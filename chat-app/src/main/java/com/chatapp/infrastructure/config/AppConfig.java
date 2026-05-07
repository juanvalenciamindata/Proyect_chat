package com.chatapp.infrastructure.config;

import com.chatapp.application.ports.MessageEventPublisherPort;
import com.chatapp.application.ports.MessageRepositoryPort;

import com.chatapp.application.usecase.SendMessageUseCase;
import com.chatapp.application.usecase.SendMessageService;
import com.chatapp.application.usecase.GetMessageHistoryUseCase;
import com.chatapp.application.usecase.GetMessagesByUserUseCase;
import com.chatapp.infrastructure.kafka.KafkaMessagePublisher;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class AppConfig {

    @Bean
    public SendMessageUseCase sendMessageUseCase(
            MessageRepositoryPort repository,
            MessageEventPublisherPort publisher
    ) {
        return new SendMessageService(repository, publisher);
    }


    @Bean
    public MessageEventPublisherPort messageEventPublisherPort(
            KafkaTemplate<String, String> kafkaTemplate,
            ObjectMapper objectMapper
    ) {
        return new KafkaMessagePublisher(kafkaTemplate, objectMapper);
    }

    @Bean
    public GetMessageHistoryUseCase getMessageHistoryUseCase(
            MessageRepositoryPort repository
    ) {
        return new GetMessageHistoryUseCase(repository);
    }

    @Bean
    public GetMessagesByUserUseCase getMessagesByUserUseCase(
            MessageRepositoryPort repository
    ) {
        return new GetMessagesByUserUseCase(repository);
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}