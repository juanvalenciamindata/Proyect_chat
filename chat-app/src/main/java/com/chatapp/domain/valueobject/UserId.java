package com.chatapp.domain.valueobject;

public record UserId(String value) {

    public UserId {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("UserId no puede ser vacío");
        }
    }
}
