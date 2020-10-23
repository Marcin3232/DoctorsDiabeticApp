package com.example.doctorsdiabeticapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.doctorsdiabeticapp.BaseActivity.BaseActivity;
import com.example.doctorsdiabeticapp.Model.Doctor;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainWindowActivity extends BaseActivity {

    String title;
    TextView Name, Surname, Email;
    FirebaseAuth mAuth;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_window);
        Initzialize();
        DataView();
        setToolbar(title);
    }

    public void Initzialize() {
        title = "Panel Obsługi";
        Name = findViewById(R.id.TextView_Name);
        Surname = findViewById(R.id.TextView_Surname);
        Email = findViewById(R.id.TextView_Email);
        mAuth = FirebaseAuth.getInstance();


    }

    public void DataView() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Doctors")
                .child(mAuth.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Doctor doctor = snapshot.getValue(Doctor.class);
                Name.setText(doctor.getName());
                Surname.setText(doctor.getSurname());
                Email.setText(doctor.getEmail());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}
