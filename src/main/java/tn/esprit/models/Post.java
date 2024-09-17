package tn.esprit.models;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;

public class Post {

    private int id;
    private int user;
    private Date date;
    private Time heure ;
    private String Description ;

    public int getId() {
        return id;
    }

    public Post() {
    }

    public Post(int id, int user, Date date, Time heure, String description) {
        this.id = id;
        this.user = user;
        this.date = date;
        this.heure = heure;
        Description = description;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getHeure() {
        return heure;
    }

    public void setHeure(Time heure) {
        this.heure = heure;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }
}
