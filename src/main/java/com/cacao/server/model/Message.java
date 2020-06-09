package com.cacao.server.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import java.time.LocalDateTime;

@Entity
public class Message {

    @Id
    private String id;
    private String from;
    private String to;
    private String message;
    private LocalDateTime time;
    private boolean wasRead;


    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public void setFrom(String from) {
        this.from = from;
    }
    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
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
