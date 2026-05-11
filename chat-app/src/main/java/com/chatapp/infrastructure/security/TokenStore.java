package com.chatapp.infrastructure.security;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class TokenStore {

    private final Set<String> tokens = ConcurrentHashMap.newKeySet();

    public void add(String token) {
        tokens.add(token);
    }

    public boolean isValid(String token) {
        return tokens.contains(token);
    }

    public void remove(String token) {
        tokens.remove(token);
    }
}