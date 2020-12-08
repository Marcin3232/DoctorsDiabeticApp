package com.example.doctorsdiabeticapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.doctorsdiabeticapp.Model.Admin;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
                        final FirebaseUser currentUser = mAuth.getCurrentUser();
                        if (currentUser != null) {
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
                                    }
                                   else if (currentUser.getEmail().equals(admin.getEmail())
                                            && admin.getVerification()) {
                                        startActivity(new Intent(getApplicationContext(),
                                                AdminPanelActivity.class));
                                        finish();
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });

                        } else {
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            finish();
                        }
                    }
                }, 2500);
            }
        }).start();
        initialize();
    }


    public void initialize() {
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
