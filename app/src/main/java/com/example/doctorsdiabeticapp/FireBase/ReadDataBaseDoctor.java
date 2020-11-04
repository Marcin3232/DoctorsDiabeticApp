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


public class ReadDataBaseDoctor {

    FirebaseAuth mAuth;
    DatabaseReference reference;
    FirebaseUser fUser;
    private Doctor doctor;

    public ReadDataBaseDoctor() {
    }

    public ReadDataBaseDoctor(FirebaseAuth mAuth, DatabaseReference reference) {
        this.mAuth = mAuth;
        this.reference = reference;
    }

    public ReadDataBaseDoctor(FirebaseAuth mAuth, DatabaseReference reference, FirebaseUser fUser) {
        this.mAuth = mAuth;
        this.reference = reference;
        this.fUser = fUser;
    }

    public void getData(final DoctorCallback callback) {
        reference = FirebaseDatabase.getInstance().getReference("Doctors")
                .child(mAuth.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                doctor = snapshot.getValue(Doctor.class);
                callback.onCallback(doctor);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                throw error.toException();
            }
        });
    }

    public void checkEmptyData(final DoctorCheckBlankFieldsCallback callback) {
        reference = FirebaseDatabase.getInstance().getReference("Doctors")
                .child(mAuth.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                doctor = snapshot.getValue(Doctor.class);
                boolean check = true;
                if (doctor.getDescribe().isEmpty() || doctor.getName().isEmpty() ||
                        doctor.getGender().isEmpty() || doctor.getSurname().isEmpty()
                        || doctor.getPhone().isEmpty() || doctor.getTitle().isEmpty()) {
                    callback.onCallback(false);
                } else {
                    callback.onCallback(true);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                throw error.toException();
            }
        });
    }


}
