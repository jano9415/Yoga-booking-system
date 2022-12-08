package com.example.yoga_booking_android.domain;

import java.util.HashSet;
import java.util.Set;

public class Yogatype {

    private long id;

    private String name;

    private String description;

    private Set<Yogatrainer> yogatrainers = new HashSet<Yogatrainer>();

    public Yogatype(){

    }

    public Yogatype(String name, String description, Set<Yogatrainer> yogatrainers) {
        this.name = name;
        this.description = description;
        this.yogatrainers = yogatrainers;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Yogatrainer> getYogatrainers() {
        return yogatrainers;
    }

    public void setYogatrainers(Set<Yogatrainer> yogatrainers) {
        this.yogatrainers = yogatrainers;
    }
}
