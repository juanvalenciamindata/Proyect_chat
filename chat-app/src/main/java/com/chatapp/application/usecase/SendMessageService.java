package com.chatapp.application.usecase;

import com.chatapp.application.ports.MessageEventPublisherPort;
import com.chatapp.application.ports.MessageRepositoryPort;
import com.chatapp.application.ports.UserRepositoryPort;
import com.chatapp.domain.exception.MessagePublishException;
import com.chatapp.domain.exception.UserNotFoundException;
import com.chatapp.domain.model.ChatMessage;
import com.chatapp.domain.valueobject.Content;
import com.chatapp.domain.valueobject.UserId;

public class SendMessageService implements SendMessageUseCase {

    private final MessageRepositoryPort repository;
    private final MessageEventPublisherPort eventPublisher;
    private final UserRepositoryPort userRepository;

    public SendMessageService(
            MessageRepositoryPort repository,
            MessageEventPublisherPort eventPublisher,
            UserRepositoryPort userRepository
    ) {
        this.repository = repository;
        this.eventPublisher = eventPublisher;
        this.userRepository = userRepository;
    }

    @Override
    public ChatMessage execute(UserId userId, Content content) {

        // Validacion critica
        userRepository.findById(userId)
                .orElseThrow(() ->
                        new UserNotFoundException(userId.value())
                );

        // Crear mensaje
        ChatMessage message = ChatMessage.create(userId, content);

        // Persistir
        repository.save(message);

        // Publicar evento
        try {
            // 🔥 Evento
            eventPublisher.publish(message);
        } catch (Exception e) {
            throw new MessagePublishException("Error publishing message");
        }
        return message;
    }
}