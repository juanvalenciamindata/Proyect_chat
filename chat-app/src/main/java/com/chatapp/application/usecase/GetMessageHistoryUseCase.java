package com.chatapp.application.usecase;

import com.chatapp.application.ports.MessageRepositoryPort;
import com.chatapp.domain.model.ChatMessage;

import java.util.List;

public class GetMessageHistoryUseCase {

    private final MessageRepositoryPort repository;

    public GetMessageHistoryUseCase(MessageRepositoryPort repository) {
        this.repository = repository;
    }

    public List<ChatMessage> execute() {
        return repository.findAll();
    }
}