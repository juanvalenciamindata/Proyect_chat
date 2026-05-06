package com.chatapp.interfaces.rest.dto;

import java.time.Instant;

public class MessageResponse {

    private String id;
    private String userId;
    private String content;
    private Instant timestamp;

    public MessageResponse(String id, String userId, String content, Instant timestamp) {
        this.id = id;
        this.userId = userId;
        this.content = content;
        this.timestamp = timestamp;
    }

    public String getId() { return id; }
    public String getUserId() { return userId; }
    public String getContent() { return content; }
    public Instant getTimestamp() { return timestamp; }
}