package com.chatapp.application.ports;

import com.chatapp.domain.model.ChatMessage;
import com.chatapp.domain.valueobject.UserId;

import java.util.List;

public interface MessageRepositoryPort {

    void save(ChatMessage message);

    List<ChatMessage> findAll();

    List<ChatMessage> findByUser(UserId userId);
}