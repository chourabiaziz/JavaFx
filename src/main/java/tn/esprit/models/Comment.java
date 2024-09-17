package tn.esprit.models;

import java.sql.Date;
import java.sql.Time;

public class Comment {

    private int id;
    private int user;
    private Date date;
    private Time heure ;
    private String Description ;
    private int postid ;

    public Comment() {

    }
    public Comment(int id, int user, Date date, Time heure, String description, int postid) {
        this.id = id;
        this.user = user;
        this.date = date;
        this.heure = heure;
        Description = description;
        this.postid = postid;
    }

    public int getId() {
        return id;
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

    public int getPostid() {
        return postid;
    }

    public void setPostid(int postid) {
        this.postid = postid;
    }


}
