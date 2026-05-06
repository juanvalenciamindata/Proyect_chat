package com.chatapp.infrastructure.persistence.entity;

import java.time.Instant;

public class MessageEntity {

    public String id;
    public String userId;
    public String content;
    public Instant timestamp;
}