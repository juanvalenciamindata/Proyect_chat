package com.chatapp.interfaces.rest.dto;

public class MessageRequest {

    private String userId;
    private String content;

    public MessageRequest() {}

    public String getUserId() {
        return userId;
    }

    public String getContent() {
        return content;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setContent(String content) {
        this.content = content;
    }
}