package com.example.doctorsdiabeticapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Timer;
import java.util.TimerTask;

public class SplashScreenActivity extends AppCompatActivity {

    ImageView ring;
    TextView progressText;
    Animation animation, animation1;
    Timer timer;
    int progressStatus = 0;
    Handler handler = new Handler();
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        new Thread(new Runnable() {
            @Override
            public void run() {
                timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        FirebaseUser currentUser = mAuth.getCurrentUser();
                        if (currentUser != null) {
                            startActivity(new Intent(getApplicationContext(), MainWindowActivity.class));
                            finish();
                        } else {
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            finish();
                        }
                    }
                }, 2500);
            }
        }).start();
        Initialize();
    }


    public void Initialize() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final ProgressBar simpleProgressBar = findViewById(R.id.ProgressBar);
                progressText = findViewById(R.id.progressText);
                while (progressStatus < 100) {
                    progressStatus += 1;
                    try {
                        Thread.sleep(20);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            simpleProgressBar.setProgress(progressStatus);
                            progressText.setText(progressStatus + "%");
                        }
                    });
                }
            }
        }).start();
        mAuth = FirebaseAuth.getInstance();
        ring = findViewById(R.id.ring);
        animation = AnimationUtils.loadAnimation(this, R.anim.scale);
        animation1 = AnimationUtils.loadAnimation(this, R.anim.set);
        ring.startAnimation(animation);

    }
}
