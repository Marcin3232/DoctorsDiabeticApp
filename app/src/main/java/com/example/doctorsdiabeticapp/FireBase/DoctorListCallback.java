package com.example.doctorsdiabeticapp.FireBase;

import com.example.doctorsdiabeticapp.Model.Doctor;

import java.util.ArrayList;

public interface DoctorListCallback {

    void onCallback(ArrayList<Doctor> value);
}
