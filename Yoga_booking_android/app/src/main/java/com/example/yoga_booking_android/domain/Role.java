package com.example.yoga_booking_android.domain;

public class Role {

    private long id;

    private String roleName;

    public Role(){

    }

    public Role(String roleName) {
        this.roleName = roleName;
    }

    public long getId() {
        return id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
