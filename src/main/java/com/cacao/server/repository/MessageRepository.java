package com.cacao.server.repository;

import com.cacao.server.model.Message;
import org.springframework.data.repository.CrudRepository;

public interface MessageRepository extends CrudRepository<Message, String> {
}
