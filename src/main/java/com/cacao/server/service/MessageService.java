package com.cacao.server.service;

import com.cacao.server.model.Message;

public interface MessageService {
    void sendMessage(Message message);

    void sendUnreadMessages(String userId);
}
