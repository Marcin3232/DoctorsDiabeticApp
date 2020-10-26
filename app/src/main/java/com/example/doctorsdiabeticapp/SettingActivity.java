package com.example.doctorsdiabeticapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.doctorsdiabeticapp.BaseActivity.BaseActivity;

public class SettingActivity extends BaseActivity implements View.OnClickListener {


    androidx.appcompat.widget.Toolbar toolbar;
    CardView card_update, card_add_photo, card_change_email;
    String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        Initialize();
    }

    public void Initialize() {
        toolbar = findViewById(R.id.toolbar);
        title = getResources().getString(R.string.setting_toolbar);
        setToolbarOther(title);
        card_add_photo = findViewById(R.id.cardView_Add_photo);
        card_change_email = findViewById(R.id.cardView_change_email_password);
        card_update = findViewById(R.id.cardView_Update);
        card_update.setOnClickListener(this);
        card_change_email.setOnClickListener(this);
        card_add_photo.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.cardView_Update:
                startActivity(new Intent(getApplicationContext(), CompletionDataActivity.class));
                break;
            case R.id.cardView_Add_photo:
                startActivity(new Intent(getApplicationContext(), AddPhotoActivity.class));
                break;
            case R.id.cardView_change_email_password:
                startActivity(new Intent(getApplicationContext(), ChangeEmailPasswordActivity.class));
                break;
            default:
                break;
        }

    }
}
