package com.chatapp.interfaces.rest.controller;

import com.chatapp.application.usecase.SendMessageUseCase;
import com.chatapp.application.usecase.GetMessageHistoryUseCase;
import com.chatapp.application.usecase.GetMessagesByUserUseCase;

import com.chatapp.domain.model.ChatMessage;
import com.chatapp.domain.valueobject.Content;
import com.chatapp.domain.valueobject.UserId;

import com.chatapp.interfaces.rest.dto.MessageRequest;
import com.chatapp.interfaces.rest.dto.MessageResponse;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/messages")
public class MessageController {

    private final SendMessageUseCase sendMessageUseCase;
    private final GetMessageHistoryUseCase historyUseCase;
    private final GetMessagesByUserUseCase userUseCase;

    public MessageController(
            SendMessageUseCase sendMessageUseCase,
            GetMessageHistoryUseCase historyUseCase,
            GetMessagesByUserUseCase userUseCase
    ) {
        this.sendMessageUseCase = sendMessageUseCase;
        this.historyUseCase = historyUseCase;
        this.userUseCase = userUseCase;
    }

    //  Enviar mensaje
    @PostMapping
    public MessageResponse send(@RequestBody MessageRequest request) {

        ChatMessage message = sendMessageUseCase.execute(
                new UserId(request.getUserId()),
                new Content(request.getContent())
        );

        return mapToResponse(message);
    }

    // Historial global
    @GetMapping("/history")
    public List<MessageResponse> history() {
        return historyUseCase.execute()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // Mensajes por usuario
    @GetMapping("/user/{userId}")
    public List<MessageResponse> byUser(@PathVariable String userId) {
        return userUseCase.execute(userId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    //  Mapper
    private MessageResponse mapToResponse(ChatMessage message) {
        return new MessageResponse(
                message.getId().value().toString(),
                message.getUserId().value(),
                message.getContent().value(),
                message.getTimestamp()
        );
    }
}