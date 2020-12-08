package com.example.doctorsdiabeticapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.doctorsdiabeticapp.BaseActivity.BaseActivity;

public class VerifiedUsersActivity extends BaseActivity {

    private String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verified_users);

        initialize();
    }

    public void initialize() {
        title = getString(R.string.verified_users_string);
        setToolbarOther(title);
    }
}