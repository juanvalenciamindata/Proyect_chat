package com.chatapp.infrastructure.memory;

import com.chatapp.application.ports.MessageRepositoryPort;
import com.chatapp.domain.model.ChatMessage;
import com.chatapp.domain.valueobject.UserId;

import java.util.ArrayList;
import java.util.List;

public class InMemoryMessageRepository implements MessageRepositoryPort {

    private final List<ChatMessage> store = new ArrayList<>();

    @Override
    public void save(ChatMessage message) {
        store.add(message);
    }

    @Override
    public List<ChatMessage> findAll() {
        return new ArrayList<>(store);
    }

    @Override
    public List<ChatMessage> findByUser(UserId userId) {
        return store.stream()
                .filter(m -> m.getUserId().equals(userId))
                .toList();
    }
}