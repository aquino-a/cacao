package com.cacao.server.service;

import com.cacao.server.model.Message;
import com.cacao.server.repository.MessageRepository;
import com.cacao.server.repository.MessageRepositoryCustom;
import com.cacao.server.utlity.Id;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService{

    @Autowired
    MessageRepository messageRepository;

    @Autowired
    MessageRepositoryCustom messageRepositoryCustom;


    @Autowired
    UserService userService;

    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;

    private static final String MESSAGE_DESTINATION = "/api/topic/message";

    @Override
    public void sendMessage(Message message) {
        if(!userService.exists(message.getToUser()))
            return;
        setTime(message);
        setId(message);
        messageRepository.save(message);
        simpMessagingTemplate.convertAndSendToUser(message.getFromUser(), MESSAGE_DESTINATION, message);
        if(!message.getToUser().equals(message.getFromUser())) {
            simpMessagingTemplate.convertAndSendToUser(message.getToUser(), MESSAGE_DESTINATION, message);
        }
    }

    @Override
    public void sendUnreadMessages(String userId) {
        var unreadMessages = messageRepository.findByToUserAndWasReadFalseOrderByTimeAsc(userId);
        unreadMessages.forEach(message -> {
            simpMessagingTemplate.convertAndSendToUser(message.getToUser(), MESSAGE_DESTINATION, message);
        });
    }

    @Override
    public List<Message> getMessages(String userId1, String userId2, LocalDateTime earlierThan) {
        return messageRepositoryCustom.findMessagesEarlierThan(userId1, userId2, earlierThan);
    }

    @Transactional
    @Override
    public void markAsRead(String messageId) {
        messageRepository.markAsRead(messageId);
    }

    private void setId(Message message) {
        var id = generateId();
        for (; messageRepository.existsById(id); id = generateId()) { }
        message.setId(id);
    }

    private void setTime(Message message) {
        message.setTime(LocalDateTime.now(ZoneOffset.UTC));
    }

    public String generateId(){
        return Id.generateOne(4);
    }
}
