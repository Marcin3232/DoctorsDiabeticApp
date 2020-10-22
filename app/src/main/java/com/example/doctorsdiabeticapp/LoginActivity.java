package com.example.doctorsdiabeticapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.doctorsdiabeticapp.AddidiotalView.Loadingbar;
import com.example.doctorsdiabeticapp.Validation.VerificationInputText;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {

    EditText Email, Password;
    Button SignIn;
    VerificationInputText verificationInputText;
    Loadingbar loadingbar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Initzialize();
        SignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Validation() == true) {
                    loadingbar.showDialog();
                    SignIn();
                } else {
                    Toast.makeText(getApplicationContext(),
                            getResources().getString(R.string.failure_sign_in),
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void Initzialize() {
        Email = findViewById(R.id.editTextEmail);
        Password = findViewById(R.id.editTextPassword);
        SignIn = findViewById(R.id.buttonSignIn);
        verificationInputText = new VerificationInputText();
        loadingbar = new Loadingbar(this);
        mAuth = FirebaseAuth.getInstance();

    }

    private boolean Validation() {
        boolean email = verificationInputText.validateEmail(Email,
                getResources().getString(R.string.write_email),
                getResources().getString(R.string.write_email_warning));
        boolean password = verificationInputText.validatePassword(Password,
                getResources().getString(R.string.write_password),
                getResources().getString(R.string.write_password_warning));
        if (!(email && password)) {
            Toast.makeText(getApplicationContext(),
                    getResources().getString(R.string.fail_sign_in),
                    Toast.LENGTH_SHORT).show();
            return false;

        } else {

            return true;
        }
    }

    public void SignIn() {
        mAuth.signInWithEmailAndPassword(Email.getText().toString(), Password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    if (user.isEmailVerified()) {
                        startActivity(new Intent(getApplicationContext(), MainWindowActivity.class));
                        finish();
                    } else {
                        user.sendEmailVerification();
                        Toast.makeText(getApplicationContext(),
                                getResources().getString(R.string.email_check_verify)
                                , Toast.LENGTH_LONG).show();
                        loadingbar.dismissbar();
                    }
                } else {
                    Toast.makeText(getApplicationContext(),
                            getResources().getString(R.string.fail_sign_in),
                            Toast.LENGTH_LONG).show();
                    loadingbar.dismissbar();
                }
            }
        });
    }

}
