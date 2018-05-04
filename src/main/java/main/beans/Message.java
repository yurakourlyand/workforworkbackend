package main.beans;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;

/**
 * @author Yura Kourlyand
 */

@Entity
public class Message {
    @Id
    @GeneratedValue
    public Long id;
    public String message;

    @Temporal(TemporalType.TIMESTAMP)
    public Date time;

    @ManyToOne()
    @JsonIgnore
    public Conversation conversation;

    public Long convId;

    public Long authorId;

    public Message(String message, Conversation conversation, Long authorId) {
        this.message = message;
        this.conversation = conversation;
        this.authorId = authorId;
        this.time = new Date();
        this.convId = conversation.id;
    }

    public Message() {
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", message='" + message + '\'' +
                ", conversationId=" + conversation.id +
                ", authorId=" + authorId +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Conversation getConversation() {
        return conversation;
    }

    public void setConversation(Conversation conversation) {
        this.conversation = conversation;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }
}
