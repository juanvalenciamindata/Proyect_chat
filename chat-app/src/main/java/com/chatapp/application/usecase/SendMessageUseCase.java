package com.chatapp.application.usecase;

import com.chatapp.domain.model.ChatMessage;
import com.chatapp.domain.valueobject.Content;
import com.chatapp.domain.valueobject.UserId;

public interface SendMessageUseCase {
    ChatMessage execute(UserId userId, Content content);
}