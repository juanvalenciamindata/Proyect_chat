package com.chatapp.domain.valueobject;

import com.chatapp.domain.exception.DomainException;

public record Content(String value) {

    public Content {
        if (value == null || value.isBlank()) {
            throw new DomainException("El contenido no puede estar vacío");
        }

        if (value.length() > 500) {
            throw new DomainException("El contenido es demasiado largo (max 500)");
        }
    }
}