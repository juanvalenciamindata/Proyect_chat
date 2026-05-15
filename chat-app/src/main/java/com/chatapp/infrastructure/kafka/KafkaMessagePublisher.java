package com.chatapp.infrastructure.kafka;

import com.chatapp.application.ports.MessageEventPublisherPort;
import com.chatapp.domain.exception.MessagePublishException;
import com.chatapp.domain.model.ChatMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Primary;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaMessagePublisher implements MessageEventPublisherPort {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public KafkaMessagePublisher(KafkaTemplate<String, String> kafkaTemplate,
                                 ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    @Override
    public void publish(ChatMessage message) {

        try {
            ChatMessageEvent event = new ChatMessageEvent(message);

            String json = objectMapper.writeValueAsString(event);

            kafkaTemplate.send(
                    "chat-global-topic",
                    json
            );

        } catch (Exception e) {
            throw new MessagePublishException("Kafka publish failed");
        }
    }
}