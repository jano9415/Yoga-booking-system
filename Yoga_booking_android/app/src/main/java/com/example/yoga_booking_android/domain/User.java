package com.example.yoga_booking_android.domain;

import java.util.HashSet;
import java.util.Set;

public class User extends Person {

    private Yogapass yogapass;

    private Role role;

    private Set<Training> trainings = new HashSet<Training>();

    private int occasionCounter;

    public User(){

    }

    public User(String emailAddress, String password, String firstName, String lastName, Yogapass yogapass, Role role,
                Set<Training> trainings , int occasionCounter) {
        super(emailAddress, password, firstName, lastName);
        this.yogapass = yogapass;
        this.role = role;
        this.trainings = trainings;
        this.occasionCounter = occasionCounter;
    }

    //Regisztrációhoz szükséges objektum
    public User(String emailAddress, String password, String firstName, String lastName){
        super(emailAddress, password, firstName, lastName);
    }

    public Yogapass getYogapass() {
        return yogapass;
    }

    public void setYogapass(Yogapass yogapass) {
        this.yogapass = yogapass;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Set<Training> getTrainings() {
        return trainings;
    }

    public void setTrainings(Set<Training> trainings) {
        this.trainings = trainings;
    }

    public int getOccasionCounter() {
        return occasionCounter;
    }

    public void setOccasionCounter(int occasionCounter) {
        this.occasionCounter = occasionCounter;
    }
}
