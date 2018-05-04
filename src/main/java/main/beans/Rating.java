package main.beans;

import javax.persistence.*;

/**
 * @author Yura Kourlyand
 */
@Entity
public class Rating {

    @Id
    @GeneratedValue
    public Long id;
    public int rating; // 0->5


    @ManyToOne
    public User ratedUser;

    @ManyToOne
    public User ratingUser;

    public Rating() {}

    public Rating(int rating, User ratedUser, User ratingUser) {
        this.rating = rating;
        this.ratedUser = ratedUser;
        this.ratingUser = ratingUser;
    }
}