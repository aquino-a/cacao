package com.cacao.server.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

/**
 * Model that represents message to and from users.
 */
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Message {

    @Id
    private String id;
    private String fromUser;
    private String toUser;
    private String message;
    private LocalDateTime time;
    private boolean wasRead;


    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public void setFromUser(String fromUser) {
        this.fromUser = fromUser;
    }
    public String getFromUser() {
        return fromUser;
    }

    public String getToUser() {
        return toUser;
    }

    public void setToUser(String toUser) {
        this.toUser = toUser;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public boolean isWasRead() {
        return wasRead;
    }

    public void setWasRead(boolean wasRead) {
        this.wasRead = wasRead;
    }
}
