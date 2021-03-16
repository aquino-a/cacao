package com.cacao.server.repository;

import com.cacao.server.model.Message;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * MessageRepository with auto generated methods.
 */
public interface MessageRepository extends CrudRepository<Message, String> {
    List<Message> findByToUserAndWasReadFalseOrderByTimeAsc(String toUser);

    @Modifying(clearAutomatically = true)
    @Query(value = "update Message m set m.wasRead=true where m.id = ?1")
    int markAsRead(String messageId);
}
