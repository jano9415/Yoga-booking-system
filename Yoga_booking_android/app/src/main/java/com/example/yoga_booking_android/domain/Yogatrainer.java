package com.example.yoga_booking_android.domain;

import java.util.HashSet;
import java.util.Set;

public class Yogatrainer extends Person{

    private String description;

    private Role role;

    private Set<Yogatype> yogatypes = new HashSet<Yogatype>();

    public Yogatrainer(){

    }

    public Yogatrainer(String emailAddress, String password, String firstName, String lastName, String description, Role role, Set<Yogatype> yogatypes) {
        super(emailAddress, password, firstName, lastName);
        this.description = description;
        this.role = role;
        this.yogatypes = yogatypes;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Set<Yogatype> getYogatypes() {
        return yogatypes;
    }

    public void setYogatypes(Set<Yogatype> yogatypes) {
        this.yogatypes = yogatypes;
    }
}
