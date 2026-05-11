package com.chatapp.interfaces.rest.controller;

import com.chatapp.application.usecase.*;
import com.chatapp.domain.model.ChatMessage;
import com.chatapp.domain.valueobject.Content;
import com.chatapp.domain.valueobject.UserId;
import com.chatapp.interfaces.rest.dto.MessageRequest;
import com.chatapp.interfaces.rest.dto.MessageResponse;
import com.chatapp.infrastructure.security.TokenStore;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/messages")
public class MessageController {

    private final SendMessageUseCase sendMessageUseCase;
    private final GetMessageHistoryUseCase historyUseCase;
    private final GetMessagesByUserUseCase userUseCase;
    private final TokenStore tokenStore;

    public MessageController(
            SendMessageUseCase sendMessageUseCase,
            GetMessageHistoryUseCase historyUseCase,
            GetMessagesByUserUseCase userUseCase,
            TokenStore tokenStore
    ) {
        this.sendMessageUseCase = sendMessageUseCase;
        this.historyUseCase = historyUseCase;
        this.userUseCase = userUseCase;
        this.tokenStore = tokenStore;
    }

    private void validateToken(String token) {
        if (token == null || !tokenStore.isValid(token)) {
            throw new RuntimeException("Unauthorized");
        }
    }

    @PostMapping
    public MessageResponse send(
            @RequestHeader("Authorization") String token,
            @RequestBody MessageRequest request
    ) {
        validateToken(token);

        ChatMessage message = sendMessageUseCase.execute(
                new UserId(request.getUserId()),
                new Content(request.getContent())
        );

        return mapToResponse(message);
    }

    @GetMapping("/history")
    public List<MessageResponse> history(
            @RequestHeader("Authorization") String token
    ) {
        validateToken(token);

        return historyUseCase.execute()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @GetMapping("/user/{userId}")
    public List<MessageResponse> byUser(
            @RequestHeader("Authorization") String token,
            @PathVariable String userId
    ) {
        validateToken(token);

        return userUseCase.execute(userId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private MessageResponse mapToResponse(ChatMessage message) {
        return new MessageResponse(
                message.getId().value().toString(),
                message.getUserId().value(),
                message.getContent().value(),
                message.getTimestamp()
        );
    }
}