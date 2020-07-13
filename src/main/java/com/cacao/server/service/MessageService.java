package com.cacao.server.service;

import com.cacao.server.model.Message;

import java.time.LocalDateTime;
import java.util.List;

public interface MessageService {
    void sendMessage(Message message);

    void sendUnreadMessages(String userId);

    List<Message> getMessages(String userId, String userId2, LocalDateTime laterThan);
}
