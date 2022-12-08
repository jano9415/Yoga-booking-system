package com.example.yoga_booking_android.domain;

public class Person {

    private long id;

    private String emailAddress;

    private String password;

    private String firstName;

    private String lastName;

    public Person(){

    }

    public Person(String emailAddress, String password, String firstName, String lastName) {
        this.emailAddress = emailAddress;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public long getId() {
        return id;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
