package com.chatapp.domain.valueobject;

import java.util.UUID;

public record UserId(String value) {

    public UserId {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("UserId no puede ser vacío");
        }
    }

    public static UserId generate() {
        return new UserId(UUID.randomUUID().toString());
    }
}