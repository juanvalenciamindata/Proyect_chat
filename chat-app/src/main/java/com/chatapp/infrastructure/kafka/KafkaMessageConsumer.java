package com.chatapp.infrastructure.kafka;

import com.chatapp.application.usecase.SendMessageUseCase;
import com.chatapp.domain.valueobject.Content;
import com.chatapp.domain.valueobject.UserId;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.annotation.KafkaListener;

public class KafkaMessageConsumer {

    private final SendMessageUseCase sendMessageUseCase;
    private final ObjectMapper objectMapper;

    public KafkaMessageConsumer(SendMessageUseCase sendMessageUseCase,
                                ObjectMapper objectMapper) {
        this.sendMessageUseCase = sendMessageUseCase;
        this.objectMapper = objectMapper;
    }

    @KafkaListener(topics = "chat-global-topic")
    public void consume(String messageJson) {

        try {
            ChatMessageEvent event =
                    objectMapper.readValue(messageJson, ChatMessageEvent.class);

            sendMessageUseCase.execute(
                    new UserId(event.getUserId()),
                    new Content(event.getContent())
            );
        } catch (Exception e) {
            throw new RuntimeException("Error consuming chat message", e);
        }
    }
}