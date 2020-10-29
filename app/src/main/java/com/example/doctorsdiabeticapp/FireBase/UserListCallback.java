package com.example.doctorsdiabeticapp.FireBase;

import com.example.doctorsdiabeticapp.Model.Doctor;
import com.example.doctorsdiabeticapp.Model.User;

import java.util.ArrayList;

public interface UserListCallback {

    void onCallback(ArrayList<User> value);
}
