package com.cacao.server.repository;

import com.cacao.server.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaContext;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class MessageRepositoryCustomImpl implements MessageRepositoryCustom {

    private static final String FIND_MESSAGES_EARLIER_THAN =
            "select m from Message m where m.time < ?3 and " +
            "((m.toUser = ?1 and m.fromUser = ?2) or (m.toUser = ?2 and m.fromUser = ?1)) " +
                    "order by m.time desc";


    private final EntityManager em;

    @Autowired
    public MessageRepositoryCustomImpl(JpaContext context) {
        this.em = context.getEntityManagerByManagedType(Message.class);
    }

    @Override
    public List<Message> findMessagesEarlierThan(String userId1, String userId2, LocalDateTime earlierThan) {
        return em.createQuery(FIND_MESSAGES_EARLIER_THAN, Message.class)
                .setParameter(1, userId1)
                .setParameter(2, userId2)
                .setParameter(3, earlierThan)
                .setMaxResults(20).getResultList();
    }
}
