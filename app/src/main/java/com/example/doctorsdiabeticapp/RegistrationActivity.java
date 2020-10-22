package com.example.doctorsdiabeticapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.doctorsdiabeticapp.AddidiotalView.Loadingbar;
import com.example.doctorsdiabeticapp.Model.Doctor;
import com.example.doctorsdiabeticapp.Validation.VerificationInputText;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;


public class RegistrationActivity extends AppCompatActivity {

    EditText Name, Surname, Email, Password, AgainPassword;
    Button Registration;
    VerificationInputText verificationInputText;
    Loadingbar loadingbar;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        Initzialize();
        Registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Validation() == true) {
                    loadingbar.showDialog();
                    Registration();
                } else {
                    Toast.makeText(getApplicationContext(),
                            getResources().getText(R.string.registration_fail),
                            Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private void Initzialize() {
        Name = findViewById(R.id.editTextName);
        Surname = findViewById(R.id.editTextSurName);
        Email = findViewById(R.id.editTextEmail);
        Password = findViewById(R.id.editTextPassword);
        AgainPassword = findViewById(R.id.editTextPasswordAgain);
        Registration = findViewById(R.id.buttonRegister);
        verificationInputText = new VerificationInputText();
        mAuth = FirebaseAuth.getInstance();
        loadingbar = new Loadingbar(this);
    }

    private boolean Validation() {
        boolean name = verificationInputText.validateName(Name,
                getResources().getString(R.string.write_name),
                getResources().getString(R.string.write_name_warning));
        boolean surname = verificationInputText.validateName(Surname,
                getResources().getString(R.string.write_surname),
                getResources().getString(R.string.write_surname_warning));
        boolean email = verificationInputText.validateEmail(Email,
                getResources().getString(R.string.write_email),
                getResources().getString(R.string.write_email_warning));
        boolean password = verificationInputText.validatePassword(Password,
                getResources().getString(R.string.write_password),
                getResources().getString(R.string.write_password_warning));
        boolean passwordAgain = verificationInputText.validateCompare(AgainPassword, Password,
                getResources().getString(R.string.write_password_again),
                getResources().getString(R.string.write_password_again_warning));
        if ((name && surname && email && password && passwordAgain) == false) {
            Toast.makeText(RegistrationActivity.this,
                    getResources().getString(R.string.correct_need_all),
                    Toast.LENGTH_SHORT).show();
            return false;

        } else {
            return name && surname && email && password && passwordAgain;
        }
    }


    public void Registration() {
        mAuth.createUserWithEmailAndPassword(Email.getText().toString(),
                Password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    Log.d("1", "createUserWithEmail:success");
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                    user.sendEmailVerification();

                    Doctor doctor = new Doctor(
                            Name.getText().toString(),
                            Surname.getText().toString(),
                            "null",
                            false,
                            user.getEmail(),
                            "null",
                            "null",
                            user.getUid()

                    );

                    FirebaseDatabase.getInstance().getReference("Doctors")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .setValue(doctor).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(getApplicationContext(),
                                    getResources().getText(R.string.registration_succesfull),
                                    Toast.LENGTH_SHORT).show();
                            loadingbar.dismissbar();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            finish();
                        }
                    });

                } else {
                    Log.e("0", "Create account fail");
                    loadingbar.dismissbar();
                    Toast.makeText(getApplicationContext(),
                            getResources().getText(R.string.registration_fail),
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

}
