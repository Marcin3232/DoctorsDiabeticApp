package com.example.doctorsdiabeticapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.doctorsdiabeticapp.BaseActivity.BaseActivity;

public class MainWindowActivity extends BaseActivity {

    String title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_window);
        Initzialize();
        setToolbar(title);
    }

    public void Initzialize(){
        title="Panel Obsługi";
    }
}
