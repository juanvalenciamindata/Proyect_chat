package com.chatapp.domain.valueobject;

public record Content(String value) {

    public Content {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("El contenido no puede estar vacío");
        }

        if (value.length() > 500) {
            throw new IllegalArgumentException("El contenido es demasiado largo (max 500)");
        }
    }
}