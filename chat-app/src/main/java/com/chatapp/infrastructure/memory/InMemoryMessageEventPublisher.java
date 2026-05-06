package com.chatapp.infrastructure.memory;

import com.chatapp.domain.model.ChatMessage;

public class InMemoryMessageEventPublisher implements com.chatapp.application.ports.MessageEventPublisherPort {

    @Override
    public void publish(ChatMessage message) {
        System.out.println("Evento publicado: " + message.getContent().value());
    }
}