package com.example.doctorsdiabeticapp;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
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

public class MainWindowActivity extends BaseActivity implements View.OnClickListener {

    String title;
    TextView Name, Surname, Email;
    FirebaseAuth mAuth;
    DatabaseReference reference;
    CardView card_Message, card_Warning, card_Setting;
    private String name;
    private String surname;
    private Boolean verification;
    private String email;
    private String describe;
    private String phone;
    private String id;
    private String gender;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_window);
        Initialize();
      // checkData();
        DataView();
        setToolbar(title);
    }

    public void Initialize() {
        title = getResources().getString(R.string.panel);
        Name = findViewById(R.id.TextView_Name);
        Surname = findViewById(R.id.TextView_Surname);
        Email = findViewById(R.id.TextView_Email);
        mAuth = FirebaseAuth.getInstance();
        card_Message = findViewById(R.id.cardView_Message);
        card_Setting = findViewById(R.id.cardView_Setting);
        card_Warning = findViewById(R.id.cardView_Warning);
        card_Message.setOnClickListener(this);
        card_Warning.setOnClickListener(this);
        card_Setting.setOnClickListener(this);
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

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cardView_Message:
                startActivity(new Intent(getApplicationContext(), CzatActivity.class));
                break;
            case R.id.cardView_Setting:
                startActivity(new Intent(getApplicationContext(), SettingActivity.class));
                break;
            case R.id.cardView_Warning:
                startActivity(new Intent(getApplicationContext(), CompletionDataActivity.class));
                break;
            default:
                break;
        }
    }

//    public void getData() {
//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//        reference = FirebaseDatabase.getInstance().getReference("Doctors")
//                .child(mAuth.getUid());
//        reference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                Doctor doctor = snapshot.getValue(Doctor.class);
//                name = doctor.getName();
//                surname = doctor.getSurname();
//                email = doctor.getEmail();
//                title = doctor.getTitle();
//                phone = doctor.getPhone();
//                describe = doctor.getDescribe();
//                gender = doctor.getGender();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Log.e("1", "Connection and take data failure!");
//            }
//        });
//
//    }
//
//    public Boolean CheckBlankValue() {
//        if (name.equals("") || surname.isEmpty() || email.isEmpty() || phone.isEmpty() ||
//                describe.isEmpty() || gender.isEmpty() || title.isEmpty()) {
//            return false;
//        } else {
//            return true;
//        }
//    }
}
