package com.example.doctorsdiabeticapp.BaseActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.doctorsdiabeticapp.CzatActivity;
import com.example.doctorsdiabeticapp.OtherClass.MessageBox;
import com.example.doctorsdiabeticapp.R;

public class BaseAdminActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private MessageBox messageBox;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        messageBox = new MessageBox(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_admin, menu);
        return true;
    }

    public void setToolbar(String title) {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }
    }


    public void setToolbarOther(String title) {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(title);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sign_out:
                messageBox.show();
                break;
            case android.R.id.home:
                onBackPressed();
                finish();
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
