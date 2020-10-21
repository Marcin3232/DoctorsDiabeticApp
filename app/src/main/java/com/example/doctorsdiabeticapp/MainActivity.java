package com.example.doctorsdiabeticapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements OnClickListener {

    Button Login;
    TextView Registration;
    View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Initzialize();

    }


    private void Initzialize() {
        Login = findViewById(R.id.login_button);
        Registration = findViewById(R.id.buttonRegister);
        Login.setOnClickListener(this);
        Registration.setOnClickListener(this);


    }

    public void onClick(View v) {
        Intent i;
        switch (v.getId()) {
            case R.id.login_button:
                i = new Intent(this, LoginActivity.class);
                startActivity(i);
                break;
            case R.id.buttonRegister:
                i = new Intent(this, RegistrationActivity.class);
                startActivity(i);
                break;
            default:
                break;

        }
    }


}
