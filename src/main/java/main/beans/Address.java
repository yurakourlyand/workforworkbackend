package main.beans;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

/**
 * @author Yura Kourlyand
 */

@Entity
public class Address {

    public Address() {
    }


    @Id
    @GeneratedValue
    public Long id;

    @OneToOne(mappedBy = "address")
    @JsonIgnore
    public User user;

    @Enumerated
    public GeneralArea generalArea;
    public String address;


    public Address(GeneralArea generalArea, String address) {
        this.generalArea = generalArea;
        this.address = address;
    }
}
