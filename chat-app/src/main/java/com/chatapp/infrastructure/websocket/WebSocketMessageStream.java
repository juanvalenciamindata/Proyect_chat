package com.chatapp.infrastructure.websocket;

import com.chatapp.infrastructure.kafka.ChatMessageEvent;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class WebSocketMessageStream {

    private final SimpMessagingTemplate template;

    public WebSocketMessageStream(SimpMessagingTemplate template) {
        this.template = template;
    }

    public void send(ChatMessageEvent event) {
        template.convertAndSend("/topic/messages", event);
    }
}