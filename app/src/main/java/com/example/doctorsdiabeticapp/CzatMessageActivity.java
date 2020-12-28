package com.example.doctorsdiabeticapp;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.doctorsdiabeticapp.Adapter.MessageAdapter;
import com.example.doctorsdiabeticapp.BaseActivity.BaseActivity;
import com.example.doctorsdiabeticapp.Model.ChatMessage;
import com.example.doctorsdiabeticapp.Model.User;
import com.example.doctorsdiabeticapp.Security.SecurityString;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CzatMessageActivity extends BaseActivity {


    CircleImageView profile_image;
    TextView user_name;
    String userId;
    EditText send_message;
    androidx.appcompat.widget.Toolbar toolbar;
    RecyclerView recyclerView;
    ImageButton btn_send;

    FirebaseUser fUser;
    DatabaseReference reference;

    MessageAdapter messageAdapter;
    List<ChatMessage> mchat;
    SecurityString securityString;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_czat_message);
        Initialize();
        setToolbarData();
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (emptyMessage() == true) {
                    sendMessage();
                } else {
                    Toast.makeText(CzatMessageActivity.this,
                            getResources().getString(R.string.empty_message_send),
                            Toast.LENGTH_SHORT).show();
                }
                send_message.setText("");
            }
        });
        reference = FirebaseDatabase.getInstance().getReference("Users").child(userId);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
//                if(user.getUrlImage().equals("")){
//                    profile_image.setImageResource(R.drawable.example_profile);
//                }
//                else {
//                    Glide.with(getApplicationContext()).load(user.getUrlImage()).into(profile_image);
//                }
                readMessage(fUser.getUid(), userId, user.getUrlImage());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                throw error.toException();
            }
        });

    }

    public void Initialize() {
        user_name = findViewById(R.id.user_name);
        intent = getIntent();
        userId = intent.getStringExtra("userId");
        fUser = FirebaseAuth.getInstance().getCurrentUser();
        toolbar = findViewById(R.id.toolbar);
        btn_send = findViewById(R.id.czat_button_send);
        send_message = findViewById(R.id.czat_text_send);
        recyclerView = findViewById(R.id.recycler_view_czat);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        reference = FirebaseDatabase.getInstance().getReference("Users").child(userId);
        profile_image = findViewById(R.id.profile_image_czat);
        securityString=new SecurityString();
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
                throw error.toException();
            }
        });
    }

    private void sendMessage() {
        ChatMessage message = new ChatMessage(
                securityString.encrypt(send_message.getText().toString()),
                fUser.getUid(),
                userId
        );
        reference = FirebaseDatabase.getInstance().getReference();

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("sender", message.getSender());
        hashMap.put("receiver", message.getReceiver());
        hashMap.put("time_send", message.getTime_send());
        hashMap.put("message", message.getMessage());

        reference.child("Chat").push().setValue(hashMap);
    }

    private void readMessage(final String messageSender, final String messageReceiver,
                             final String imageurl) {
        mchat = new ArrayList<>();
        reference = FirebaseDatabase.getInstance().getReference("Chat");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mchat.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    ChatMessage chatMessage = dataSnapshot.getValue(ChatMessage.class);
                    if (chatMessage.getReceiver().equals(messageSender) && chatMessage.getSender().equals(messageReceiver) ||
                            chatMessage.getReceiver().equals(messageReceiver) &&
                                    chatMessage.getSender().equals(messageSender)) {
                        mchat.add(chatMessage);
                    }


                    messageAdapter = new MessageAdapter(getApplicationContext(), mchat, imageurl);
                    recyclerView.setAdapter(messageAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                throw error.toException();
            }
        });
    }
}
