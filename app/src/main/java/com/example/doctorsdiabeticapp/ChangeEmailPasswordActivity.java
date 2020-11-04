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
import com.example.doctorsdiabeticapp.BaseActivity.BaseActivity;
import com.example.doctorsdiabeticapp.Validation.VerificationInputText;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ChangeEmailPasswordActivity extends BaseActivity implements View.OnClickListener {

    androidx.appcompat.widget.Toolbar toolbar;
    String title;
    EditText current_password, new_password, repeat_password, new_email, password;
    Button commit_password, commit_email;
    FirebaseAuth mAuth;
    Loadingbar loadingbar;
    VerificationInputText verificationInputText;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_email_password);
        Initialize();
    }

    public void Initialize() {
        toolbar = findViewById(R.id.toolbar);
        title = getResources().getString(R.string.editing_data);
        setToolbarOther(title);
        current_password = findViewById(R.id.edit_current_password);
        new_password = findViewById(R.id.edit_password_new);
        repeat_password = findViewById(R.id.edit_password_repeat);
        new_email = findViewById(R.id.edit_new_email);
        password = findViewById(R.id.edit_password);
        commit_email = findViewById(R.id.commit_change_email_button);
        commit_password = findViewById(R.id.commit_change_password_button);
        commit_password.setOnClickListener(this);
        commit_email.setOnClickListener(this);
        mAuth = FirebaseAuth.getInstance();
        loadingbar = new Loadingbar(this);
        verificationInputText = new VerificationInputText();
        user = FirebaseAuth.getInstance().getCurrentUser();
    }


    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.commit_change_password_button:
                if (validationPassword() == true) {
                    loadingbar.showDialog();
                    changePassword();
                } else {
                    Toast.makeText(getApplicationContext(),
                            getResources().getString(R.string.empty_EditText),
                            Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.commit_change_email_button:
                if (validationEmail() == true) {
                    loadingbar.showDialog();
                    changeEmail();
                } else {
                    Toast.makeText(getApplicationContext(),
                            getResources().getString(R.string.empty_EditText),
                            Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    public boolean validationPassword() {

        boolean password_old = verificationInputText.validateCheckEmpty(current_password,
                getResources().getString(R.string.write_password));
        boolean password = verificationInputText.validatePassword(new_password,
                getResources().getString(R.string.write_password),
                getResources().getString(R.string.write_password_warning));
        boolean passwordAgain = verificationInputText.validateCompare(repeat_password, new_password,
                getResources().getString(R.string.write_password_again),
                getResources().getString(R.string.write_password_again_warning));
        if ((password_old && password && passwordAgain) == false) {
            Toast.makeText(getApplicationContext(),
                    getResources().getString(R.string.correct_need_all),
                    Toast.LENGTH_SHORT).show();
            return false;
        } else {

            return password_old && password && passwordAgain;
        }
    }

    public boolean validationEmail() {
        boolean email = verificationInputText.validateEmail(new_email,
                getResources().getString(R.string.write_email),
                getResources().getString(R.string.write_email_warning));
        if (email == false) {
            Toast.makeText(getApplicationContext(),
                    getResources().getString(R.string.wrong_something),
                    Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    public void changePassword() {

        AuthCredential credential = EmailAuthProvider
                .getCredential(user.getEmail(), current_password.getText().toString());
        user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    user.updatePassword(new_password.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        loadingbar.dismissbar();
                                        Toast.makeText(getApplicationContext(),
                                                getResources().getString(R.string.password_update),
                                                Toast.LENGTH_SHORT).show();
                                        mAuth.signOut();
                                        startActivity(new Intent(getApplicationContext(),
                                                LoginActivity.class));

                                    } else {
                                        loadingbar.dismissbar();
                                        Toast.makeText(getApplicationContext(),
                                                getResources().getString(R.string.password_current_fail),
                                                Toast.LENGTH_SHORT).show();
                                        Log.d("1", "Password not update, do something wrong");
                                    }

                                }
                            });
                } else {
                    loadingbar.dismissbar();
                    Toast.makeText(getApplicationContext(),
                            getResources().getString(R.string.password_current_fail),
                            Toast.LENGTH_SHORT).show();
                    Log.d("1", "Error authentications, password not update");
                }
            }
        });
    }

    public void changeEmail() {
        AuthCredential credential = EmailAuthProvider.getCredential(user.getEmail(),
                password.getText().toString());
        user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {

                    user.updateEmail(new_email.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        loadingbar.dismissbar();
                                        Toast.makeText(getApplicationContext(),
                                                getResources().getString(R.string.email_upadate),
                                                Toast.LENGTH_SHORT).show();
                                        mAuth.signOut();
                                        startActivity(new Intent(getApplicationContext(),
                                                LoginActivity.class));

                                    } else {
                                        loadingbar.dismissbar();
                                        Toast.makeText(getApplicationContext(),
                                                getResources().getString(R.string.email_update_fail),
                                                Toast.LENGTH_SHORT).show();
                                        Log.d("1", "Update new email is fail");
                                    }
                                }
                            });
                } else {
                    loadingbar.dismissbar();
                    Toast.makeText(getApplicationContext(),
                            getResources().getString(R.string.password_current_fail),
                            Toast.LENGTH_SHORT).show();
                    Log.d("1", "Password is uncorrected");
                }
            }
        });

    }
}
