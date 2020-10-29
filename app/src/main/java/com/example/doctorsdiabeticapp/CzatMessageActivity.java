package com.example.doctorsdiabeticapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.example.doctorsdiabeticapp.BaseActivity.BaseActivity;
import com.example.doctorsdiabeticapp.Model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class CzatMessageActivity extends BaseActivity {


    CircleImageView profile_image;
    TextView user_name;
    String userId;
    EditText send_message;
    androidx.appcompat.widget.Toolbar toolbar;

    FirebaseUser fUser;
    DatabaseReference reference;

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_czat_message);
        Initialize();
        setToolbarData();
    }

    public void Initialize() {
        user_name = findViewById(R.id.user_name);
        intent = getIntent();
        userId = intent.getStringExtra("userId");
        fUser = FirebaseAuth.getInstance().getCurrentUser();
        toolbar = findViewById(R.id.toolbar);
        send_message = findViewById(R.id.czat_text_send);

        reference = FirebaseDatabase.getInstance().getReference("Users").child(userId);
    }

    private boolean emptyMessage() {
        String msg = send_message.getText().toString();
        if (!msg.equals("")) {
            return true;
        } else {
            return false;
        }
    }

    public void setToolbarData() {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                String title = user.getName();
                setToolbarOther(title);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
