package com.chatapp.domain.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String userId) {
        super("El usuario no funciona: " + userId);
    }
}
