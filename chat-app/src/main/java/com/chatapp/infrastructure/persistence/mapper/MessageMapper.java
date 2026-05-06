package com.chatapp.infrastructure.persistence.mapper;

import com.chatapp.domain.model.ChatMessage;
import com.chatapp.domain.valueobject.Content;
import com.chatapp.domain.valueobject.MessageId;
import com.chatapp.domain.valueobject.UserId;
import com.chatapp.infrastructure.persistence.entity.MessageEntity;

public class MessageMapper {

    public static MessageEntity toEntity(ChatMessage message) {
        MessageEntity entity = new MessageEntity();
        entity.id = message.getId().value();
        entity.userId = message.getUserId().value();
        entity.content = message.getContent().value();
        entity.timestamp = message.getTimestamp();
        return entity;
    }

    public static ChatMessage toDomain(MessageEntity entity) {
        return ChatMessage.restore(
                new MessageId(entity.id),
                new UserId(entity.userId),
                new Content(entity.content),
                entity.timestamp
        );
    }
}