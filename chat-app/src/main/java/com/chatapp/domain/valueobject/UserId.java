package com.chatapp.domain.valueobject;

import com.chatapp.domain.exception.DomainException;

public record UserId(String value) {

    public UserId {
        if (value == null || value.isBlank()) {
            throw new DomainException("UserId no puede ser vacío");
        }
    }
}
