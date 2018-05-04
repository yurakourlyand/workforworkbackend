package main.beans;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.repository.cdi.Eager;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Yura Kourlyand
 */


@Entity
public class User {

    @Id
    @GeneratedValue
    public Long id;

    @Column(unique = true)
    public String userName;


    private String password;

    @Column(unique = true)
    public String email;

    public String fullName;
    public Integer age;
    public String summary;
    public String profileImagePath;

    @ManyToMany()
    @LazyCollection(LazyCollectionOption.FALSE)
    public List<Conversation> conversations;


    @OneToOne(cascade = CascadeType.ALL)
    public Address address;

    @Temporal(TemporalType.TIMESTAMP)
    public Date dateJoined;

    @Temporal(TemporalType.TIMESTAMP)
    public Date lastTimeOnline;


    @ManyToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    @OrderBy("name ASC")
    public List<Tag> tags;

    @ManyToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    @OrderBy("name ASC")
    public List<Tag> requiredTags;

    public User() {}

    public User(String userName, String password, String email, String fullName) {
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.fullName = fullName;
        conversations = new ArrayList<>();
        this.dateJoined = new Date();
    }

    @JsonIgnore
    public String getPassword() {
        return password;
    }
    @JsonProperty
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", fullName='" + fullName + '\'' +
                ", age=" + age +
                ", summary='" + summary + '\'' +
                ", profileImagePath='" + profileImagePath + '\'' +
                ", dateJoined=" + dateJoined +
                ", lastTimeOnline=" + lastTimeOnline +
                ", tags=" + tags +
                ", required=" + requiredTags +
                '}';
    }

}
