package main.beans;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Yura Kourlyand
 */

@Entity
public class Conversation {
    @Id
    @GeneratedValue
    public Long id;
    public long userAId;
    public long userBId;

    @ManyToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    public List<User> usersThatRemovedConv;

    @OneToMany(cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    @OrderBy("time ASC ")
    public List<Message> messages;


    public Conversation(long userAId, long userBId) {
        this.usersThatRemovedConv = new ArrayList<>();
        this.userAId = userAId;
        this.userBId = userBId;
        this.messages = new ArrayList<>();
    }

    public Conversation() {
    }

    @Override
    public String toString() {
        return "Conversation{" +
                "id=" + id +
                ", userAId=" + userAId +
                ", userBId=" + userBId +
                ", messages=" + messages +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
}
