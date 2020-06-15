package com.cacao.server.repository;

import com.cacao.server.model.Message;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MessageRepository extends CrudRepository<Message, String> {
    List<Message> findByToUserAndWasReadFalseOrderByTimeAsc(String toUser);
}
