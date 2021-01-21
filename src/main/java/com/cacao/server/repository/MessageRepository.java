package com.cacao.server.repository;

import com.cacao.server.model.Message;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface MessageRepository extends CrudRepository<Message, String> {
    List<Message> findByToUserAndWasReadFalseOrderByTimeAsc(String toUser);

    @Query("select m from Message m where m.time < ?3 and ((m.toUser = ?1 and m.fromUser = ?2) or (m.toUser = ?2 and m.fromUser = ?1))")
    List<Message> findMessagesEarlierThan(String userId1, String userId2, LocalDateTime earlierThan);

    @Modifying(clearAutomatically = true)
    @Query(value = "update Message m set m.wasRead=true where m.id = ?1")
    int markAsRead(String messageId);
}
