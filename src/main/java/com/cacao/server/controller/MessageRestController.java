package com.cacao.server.controller;

import com.cacao.server.model.Message;
import com.cacao.server.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Rest api for handling messages.
 */
@RestController
@RequestMapping("/api/message")
public class MessageRestController {

    @Autowired
    private MessageService messageService;

    @GetMapping("")
    public List<Message> getMessages(Principal principal, @RequestParam String userId2,
                                     @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime earlierThan){
        return messageService.getMessages(principal.getName(), userId2, earlierThan);
    }

}
