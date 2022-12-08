package com.example.yoga_booking_android.domain;

public class Yogapass {

    private long id;

    private String description;

    private int occasionNumber;

    private int price;

    public Yogapass(){

    }

    public Yogapass(String description, int occasionNumber, int price) {
        this.description = description;
        this.occasionNumber = occasionNumber;
        this.price = price;
    }

    public long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getOccasionNumber() {
        return occasionNumber;
    }

    public void setOccasionNumber(int occasionNumber) {
        this.occasionNumber = occasionNumber;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
