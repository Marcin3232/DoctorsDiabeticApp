package com.example.doctorsdiabeticapp;

import androidx.annotation.NonNull;

import android.os.Bundle;
import android.view.Window;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.doctorsdiabeticapp.BaseActivity.BaseActivity;
import com.example.doctorsdiabeticapp.Model.Doctor;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;

import java.util.ArrayList;



public class CompletionDataActivity extends BaseActivity {

    EditText Name, Surname, Title, Email, Phone, Describe;
    Spinner Gender;
    FirebaseAuth mAuth;
    DatabaseReference reference;
    ArrayList<Doctor> doctors;

   String licz;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completion_data);
        Initialize();
        getData();
        Name.setText(licz);

    }

    public void Initialize() {
        Name = findViewById(R.id.edit_Name);
        Surname = findViewById(R.id.edit_Surname);
        Gender = findViewById(R.id.spinner_gender);
        Title = findViewById(R.id.edit_Title);
        Email = findViewById(R.id.edit_Email);
        Phone = findViewById(R.id.edit_Phone);
        Describe = findViewById(R.id.edit_Describe);
        mAuth = FirebaseAuth.getInstance();
        doctors=new ArrayList<>();

    }


    public void getData() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Doctors")
                .child(mAuth.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                licz=snapshot.getValue(Doctor.class).getName();
                 Doctor doctor = snapshot.getValue(Doctor.class);

//             Name.setText(licz);
//                Surname.setText(doctor.getSurname());
//                Email.setText(doctor.getEmail());

                if(doctor.getDescribe().equals("null")){

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }


}
