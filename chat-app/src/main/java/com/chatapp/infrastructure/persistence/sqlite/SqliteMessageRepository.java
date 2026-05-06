package com.chatapp.infrastructure.persistence.sqlite;

import com.chatapp.application.ports.MessageRepositoryPort;
import com.chatapp.domain.model.ChatMessage;
import com.chatapp.domain.valueobject.UserId;

import java.util.ArrayList;
import java.util.List;

public class SqliteMessageRepository implements MessageRepositoryPort {

    private final List<ChatMessage> database = new ArrayList<>();

    @Override
    public void save(ChatMessage message) {
        database.add(message);
    }

    @Override
    public List<ChatMessage> findAll() {
        return new ArrayList<>(database);
    }

    @Override
    public List<ChatMessage> findByUser(UserId userId) {
        return database.stream()
                .filter(m -> m.getUserId().equals(userId))
                .toList();
    }
}