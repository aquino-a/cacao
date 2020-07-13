package com.cacao.server.controller;

import com.cacao.server.model.Message;
import com.cacao.server.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Controller
@MessageMapping("/message")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @MessageMapping("/send")
    public Message sendMessage(Principal principal, Message message) {
        message.setFromUser(principal.getName());
        CompletableFuture.runAsync(() -> messageService.sendMessage(message));
        return message;
    }

    @MessageMapping("/")
    public List<Message> getMessages(Principal principal, String userId2, LocalDateTime earlierThan){
        return messageService.getMessages(principal.getName(), userId2, earlierThan);
    }
}
