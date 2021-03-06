package main.beans;

import javax.persistence.*;

/**
 * @author Yura Kourlyand
 */


@Entity
public class Tag {

    @Id
    @GeneratedValue
    public Long id;

    @Column(unique = true)
    public String name;

    public String hebrewName;

    public Tag() {}

    public Tag(String name, String hebrewName) {
        this.name = name;
        this.hebrewName = hebrewName;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Tag && ((Tag) obj).id.equals(this.id);
    }

    @Override
    public String toString() {
        return "Tag{" +
                "name='" + name + '\'' +
                ", hebrewName='" + hebrewName + '\'' +
                '}';
    }
}
