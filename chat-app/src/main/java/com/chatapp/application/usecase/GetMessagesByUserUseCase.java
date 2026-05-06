package com.chatapp.application.usecase;

import com.chatapp.application.ports.MessageRepositoryPort;
import com.chatapp.domain.model.ChatMessage;
import com.chatapp.domain.valueobject.UserId;

import java.util.List;

public class GetMessagesByUserUseCase {

    private final MessageRepositoryPort repository;

    public GetMessagesByUserUseCase(MessageRepositoryPort repository) {
        this.repository = repository;
    }

    public List<ChatMessage> execute(String userId) {
        return repository.findByUser(new UserId(userId));
    }
}