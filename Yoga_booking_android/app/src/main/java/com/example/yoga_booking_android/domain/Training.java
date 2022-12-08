package com.example.yoga_booking_android.domain;

import java.util.HashSet;
import java.util.Set;

public class Training {

    private long id;

    //private Date date;

    private String date;

    //private Time startingTime;

    //private Time finishingTime;

    private String startingTime;

    private String finishingTime;

    private String language;

    private Yogatype yogatype;

    private Yogatrainer yogatrainer;

    private int maxCapacity;

    private Set<User> users = new HashSet<User>();

    public Training(){

    }

    public Training(/*Date date*/String date, /*Time startingTime*/String startingTime, /*Time finishingTime*/String finishingTime, String language, Yogatype yogatype,
                    Yogatrainer yogatrainer, int maxCapacity, Set<User> users) {
        this.date = date;
        this.startingTime = startingTime;
        this.finishingTime = finishingTime;
        this.language = language;
        this.yogatype = yogatype;
        this.yogatrainer = yogatrainer;
        this.maxCapacity = maxCapacity;
        this.users = users;
    }

    public long getId() {
        return id;
    }

    /*public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }*/

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    /*public Time getStartingTime() {
        return startingTime;
    }

    public void setStartingTime(Time startingTime) {
        this.startingTime = startingTime;
    }

    public Time getFinishingTime() {
        return finishingTime;
    }

    public void setFinishingTime(Time finishingTime) {
        this.finishingTime = finishingTime;
    }*/

    public String getStartingTime() {
        return startingTime;
    }

    public void setStartingTime(String startingTime) {
        this.startingTime = startingTime;
    }

    public String getFinishingTime() {
        return finishingTime;
    }

    public void setFinishingTime(String finishingTime) {
        this.finishingTime = finishingTime;
    }



    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Yogatype getYogatype() {
        return yogatype;
    }

    public void setYogatype(Yogatype yogatype) {
        this.yogatype = yogatype;
    }

    public Yogatrainer getYogatrainer() {
        return yogatrainer;
    }

    public void setYogatrainer(Yogatrainer yogatrainer) {
        this.yogatrainer = yogatrainer;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
}
