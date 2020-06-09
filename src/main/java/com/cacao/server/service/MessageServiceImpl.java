package com.cacao.server.service;

import com.cacao.server.model.Message;
import com.cacao.server.repository.MessageRepository;
import com.cacao.server.utlity.Id;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class MessageServiceImpl implements MessageService{

    @Autowired
    MessageRepository messageRepository;

    @Autowired
    UserService userService;

    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;

    private static final String MESSAGE_DESTINATION = "/api/topic/message";

    @Override
    public void sendMessage(Message message) {
        if(!userService.exists(message.getTo()))
            return;
        setId(message);
        messageRepository.save(message);
        simpMessagingTemplate.convertAndSendToUser(message.getFrom(), MESSAGE_DESTINATION, message);
        simpMessagingTemplate.convertAndSendToUser(message.getTo(), MESSAGE_DESTINATION, message);
    }

    private void setId(Message message) {
        var id = generateId();
        for (; messageRepository.existsById(id); id = generateId()) {
        }
        message.setId(id);
    }

    public String generateId(){
        return Id.generateOne(4);
    }
}
