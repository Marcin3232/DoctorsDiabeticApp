package com.example.doctorsdiabeticapp.BaseActivity;

import android.content.Intent;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.doctorsdiabeticapp.CzatActivity;
import com.example.doctorsdiabeticapp.OtherClass.MessageBox;
import com.example.doctorsdiabeticapp.R;
import static com.example.doctorsdiabeticapp.R.*;

public class BaseActivity extends AppCompatActivity {

    Toolbar toolbar;
    MessageBox messageBox;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        messageBox=new MessageBox(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    public void setToolbar(String title) {
        toolbar = findViewById(id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case id.message:
                startActivity(new Intent(getApplicationContext(), CzatActivity.class));
                finish();
                break;
            case id.refresh:
                recreate();
                break;
            case id.sign_out:
                messageBox.show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
