package com.example.doctorsdiabeticapp.Model;


public class User {
    private String name;
    private String id;
    private String urlImage;

    public User() {

    }

    public User(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public User(String name, String id, String urlImage) {
        this.name = name;
        this.id = id;
        this.urlImage = urlImage;
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

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }
}
