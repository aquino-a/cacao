package com.cacao.server.repository;

import com.cacao.server.model.Message;

import java.time.LocalDateTime;
import java.util.List;

public interface MessageRepositoryCustom {

    List<Message> findMessagesEarlierThan(String userId1, String userId2, LocalDateTime earlierThan);
}
