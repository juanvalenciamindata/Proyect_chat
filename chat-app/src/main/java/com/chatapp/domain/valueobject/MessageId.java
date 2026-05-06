package com.chatapp.domain.valueobject;

import java.util.UUID;

public record MessageId(String value) {

    public static MessageId newId() {
        return new MessageId(UUID.randomUUID().toString());
    }
}