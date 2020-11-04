package com.example.doctorsdiabeticapp.Model;

public class Doctor {

    private String name;
    private String surname;
    private String title;
    private Boolean verification;
    private String email;
    private String describe;
    private String phone;
    private String id;
    private String gender;

    public Doctor() {
    }

    public Doctor(String name, String surname, String title, Boolean verification, String describe, String phone, String id,String gender) {
        this.name = name;
        this.surname = surname;
        this.title = title;
        this.verification = verification;
        this.describe = describe;
        this.phone = phone;
        this.id = id;
        this.gender=gender;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getVerification() {
        return verification;
    }

    public void setVerification(Boolean verification) {
        this.verification = verification;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
