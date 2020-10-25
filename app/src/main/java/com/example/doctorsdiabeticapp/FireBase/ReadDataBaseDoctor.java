package com.example.doctorsdiabeticapp.FireBase;

import androidx.annotation.NonNull;

import com.example.doctorsdiabeticapp.Model.Doctor;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ReadDataBaseDoctor {

    FirebaseAuth mAuth;
    DatabaseReference reference;
    Doctor doctor;

    public ReadDataBaseDoctor() {
    }

    public ReadDataBaseDoctor(FirebaseAuth mAuth,DatabaseReference reference) {
        this.mAuth = mAuth;
        this.reference=reference;
    }

    public void getData() {

        reference = FirebaseDatabase.getInstance().getReference("Doctors")
                .child(mAuth.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                 doctor = snapshot.getValue(Doctor.class);

                if(doctor.getDescribe().equals("null")){

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
}

public Doctor getDoctor(){
        return doctor;
}
}
