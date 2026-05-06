package com.chatapp.application.ports;

import com.chatapp.domain.model.ChatMessage;

public interface MessageEventPublisherPort {

    void publish(ChatMessage message);
}