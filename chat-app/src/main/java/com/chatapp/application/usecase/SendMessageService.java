package com.chatapp.application.usecase;

import com.chatapp.application.ports.MessageEventPublisherPort;
import com.chatapp.application.ports.MessageRepositoryPort;
import com.chatapp.domain.model.ChatMessage;
import com.chatapp.domain.valueobject.Content;
import com.chatapp.domain.valueobject.UserId;

public class SendMessageService implements SendMessageUseCase {

    private final MessageRepositoryPort repository;
    private final MessageEventPublisherPort eventPublisher;

    public SendMessageService(MessageRepositoryPort repository,
                              MessageEventPublisherPort eventPublisher) {
        this.repository = repository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public ChatMessage execute(UserId userId, Content content) {

        ChatMessage message = ChatMessage.create(userId, content);

        repository.save(message);
        eventPublisher.publish(message);

        return message;
    }
}
