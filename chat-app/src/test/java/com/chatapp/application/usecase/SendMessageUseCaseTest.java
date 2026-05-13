package com.chatapp.application.usecase;

import com.chatapp.application.ports.MessageEventPublisherPort;
import com.chatapp.application.ports.MessageRepositoryPort;
import com.chatapp.application.ports.UserRepositoryPort;
import com.chatapp.domain.model.ChatMessage;
import com.chatapp.domain.valueobject.Content;
import com.chatapp.domain.valueobject.UserId;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.mockito.Mockito.*;

class SendMessageServiceTest {

    @Test
    void shouldSendMessageSuccessfully() {

        MessageRepositoryPort repository = mock(MessageRepositoryPort.class);
        MessageEventPublisherPort publisher = mock(MessageEventPublisherPort.class);
        UserRepositoryPort userRepository = mock(UserRepositoryPort.class);

        when(userRepository.findById(any())).thenReturn(Optional.of(mock()));

        SendMessageUseCase useCase = new SendMessageService(
                repository,
                publisher,
                userRepository
        );

        UserId userId = new UserId("1");
        Content content = new Content("hola test");

        ChatMessage result = useCase.execute(userId, content);

        verify(repository, times(1)).save(any());
        verify(publisher, times(1)).publish(any());

        assert result != null;
    }
}