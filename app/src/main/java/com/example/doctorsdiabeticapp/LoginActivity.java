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
import com.example.doctorsdiabeticapp.Model.Admin;
import com.example.doctorsdiabeticapp.Validation.VerificationInputText;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    EditText email, password;
    Button signin;
    VerificationInputText verificationInputText;
    Loadingbar loadingbar;
    private String x;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initialize();

    }

    public void initialize() {
        email = findViewById(R.id.editTextEmail);
        password = findViewById(R.id.editTextPassword);
        signin = findViewById(R.id.buttonSignIn);
        signin.setOnClickListener(this);
        verificationInputText = new VerificationInputText();
        loadingbar = new Loadingbar(this);
        mAuth = FirebaseAuth.getInstance();

    }

    private boolean validation() {
        boolean email = verificationInputText.validateEmail(this.email,
                getResources().getString(R.string.write_email),
                getResources().getString(R.string.write_email_warning));
        boolean password = verificationInputText.validatePassword(this.password,
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

    public void signIn() {
        mAuth.signInWithEmailAndPassword(email.getText().toString(),
                password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                    if (user.isEmailVerified()) {
                        DatabaseReference reference = FirebaseDatabase.getInstance()
                                .getReference("Admin").child(mAuth.getUid());
                        reference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                Admin admin = snapshot.getValue(Admin.class);
                                if (admin == null) {
                                    startActivity(new Intent(getApplicationContext(),
                                            MainWindowActivity.class));
                                    finish();
                                } else if (user.getEmail().equals(admin.getEmail())
                                        && admin.getVerification()) {
                                    startActivity(new Intent(getApplicationContext(),
                                            AdminPanelActivity.class));
                                    finish();
                                }
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Log.e("TAG", "Exception: " + error.toString());
                            }
                        });

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


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonSignIn:
                if (validation() == true) {
                    loadingbar.showDialog();
                    signIn();
                } else {
                    Toast.makeText(getApplicationContext(),
                            getResources().getString(R.string.failure_sign_in),
                            Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }
}
