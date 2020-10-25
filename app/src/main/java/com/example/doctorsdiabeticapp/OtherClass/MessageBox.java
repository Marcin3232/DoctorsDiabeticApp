package com.example.doctorsdiabeticapp.OtherClass;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;


import com.example.doctorsdiabeticapp.MainActivity;
import com.example.doctorsdiabeticapp.R;
import com.google.firebase.auth.FirebaseAuth;

public class MessageBox extends Dialog implements android.view.View.OnClickListener {

    public Activity activity;
    public Dialog dialog;
    public Button commit, cancel;
    FirebaseAuth mAuth;

    public MessageBox( Activity activity) {
        super(activity);
        this.activity = activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Initialize();

    }

    public void Initialize() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.message_box);
        commit = findViewById(R.id.Commit);
        cancel = findViewById(R.id.Cancel);
        commit.setOnClickListener(this);
        cancel.setOnClickListener(this);
        mAuth=FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.Commit:
                mAuth.signOut();
                activity.startActivity(new Intent(getContext(), MainActivity.class));
                activity.finish();
                break;
            case R.id.Cancel:
                dismiss();
                break;
            default:
                break;
        }
        dismiss();
    }
}
