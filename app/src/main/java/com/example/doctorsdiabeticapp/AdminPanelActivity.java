package com.example.doctorsdiabeticapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.doctorsdiabeticapp.BaseActivity.BaseActivity;
import com.example.doctorsdiabeticapp.BaseActivity.BaseAdminActivity;
import com.example.doctorsdiabeticapp.Model.Admin;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AdminPanelActivity extends BaseAdminActivity implements View.OnClickListener {

    private String title;
    private CardView cardViewVerified, cardViewVerify, cardViewSettings, cardViewAddAdmin;
    private TextView name, email;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);

        initialize();
        dataView();
    }

    private void initialize() {
        title = getString(R.string.admin_panel);
        setToolbar(title);
        cardViewAddAdmin = findViewById(R.id.cardView_Add_admin);
        cardViewSettings = findViewById(R.id.cardView_Setting);
        cardViewVerified = findViewById(R.id.cardView_Verification);
        cardViewVerify = findViewById(R.id.cardView_View_Users);
        cardViewVerify.setOnClickListener(this);
        cardViewVerified.setOnClickListener(this);
        cardViewSettings.setOnClickListener(this);
        cardViewAddAdmin.setOnClickListener(this);
        name = findViewById(R.id.TextView_Name);
        email = findViewById(R.id.TextView_Email);
        mAuth = FirebaseAuth.getInstance();
    }

    public void dataView() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Admin")
                .child(mAuth.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Admin admin = snapshot.getValue(Admin.class);
                name.setText(admin.getName());
                email.setText(admin.getEmail());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("TAG", "Exception: " + error.toString());
            }
        });
    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.cardView_Add_admin:
                break;
            case R.id.cardView_View_Users:
                startActivity(new Intent(getApplicationContext(), VerifiedUsersActivity.class));
                break;
            case R.id.cardView_Verification:
                startActivity(new Intent(getApplicationContext(), VerifyUsersActivity.class));
                break;
            case R.id.cardView_Setting:
                break;
            default:
                break;
        }
    }
}
