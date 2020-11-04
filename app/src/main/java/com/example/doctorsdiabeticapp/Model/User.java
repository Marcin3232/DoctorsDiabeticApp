package com.example.doctorsdiabeticapp.Model;


public class User {
    private String name;
    private String id;

    public User() {

    }

    public User(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }


    public String getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }


    public void setId(String id) {
        this.id = id;
    }
}
