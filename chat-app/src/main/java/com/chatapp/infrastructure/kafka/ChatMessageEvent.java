package com.chatapp.infrastructure.kafka;

import com.chatapp.domain.model.ChatMessage;

public class ChatMessageEvent {

    private String id;
    private String userId;
    private String content;
    private String timestamp;

    public ChatMessageEvent(ChatMessage message) {
        this.id = message.getId().value().toString();
        this.userId = message.getUserId().value();
        this.content = message.getContent().value();
        this.timestamp = message.getTimestamp().toString();
    }

    public String getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getContent() {
        return content;
    }

    public String getTimestamp() {
        return timestamp;
    }
}