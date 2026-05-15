package com.chatapp.domain.exception;

public class MessagePublishException extends RuntimeException {
    public MessagePublishException(String message) {
        super(message);
    }
}
