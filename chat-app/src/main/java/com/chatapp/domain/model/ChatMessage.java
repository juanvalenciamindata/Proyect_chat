package com.chatapp.domain.model;

import com.chatapp.domain.valueobject.Content;
import com.chatapp.domain.valueobject.MessageId;
import com.chatapp.domain.valueobject.UserId;

import java.time.Instant;
import java.util.UUID;

public class ChatMessage {

    private MessageId id;
    private UserId userId;
    private Content content;
    private Instant timestamp;

    private ChatMessage() {}

    // Crear nuevo mensaje
    public static ChatMessage create(UserId userId, Content content) {
        ChatMessage message = new ChatMessage();
        message.id = new MessageId(UUID.randomUUID().toString());
        message.userId = userId;
        message.content = content;
        message.timestamp = Instant.now();
        return message;
    }

    // Reconstruir desde BD
    public static ChatMessage restore(
            MessageId id,
            UserId userId,
            Content content,
            Instant timestamp
    ) {
        ChatMessage message = new ChatMessage();
        message.id = id;
        message.userId = userId;
        message.content = content;
        message.timestamp = timestamp;
        return message;
    }

    // getters
    public MessageId getId() {
        return id;
    }

    public UserId getUserId() {
        return userId;
    }

    public Content getContent() {
        return content;
    }

    public Instant getTimestamp() {
        return timestamp;
    }
}