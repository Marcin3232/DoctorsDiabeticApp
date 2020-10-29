package com.example.doctorsdiabeticapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.Toast;

import com.example.doctorsdiabeticapp.Adapter.UserAdapter;
import com.example.doctorsdiabeticapp.BaseActivity.BaseActivity;
import com.example.doctorsdiabeticapp.FireBase.UserListCallback;
import com.example.doctorsdiabeticapp.Model.ChatMessage;
import com.example.doctorsdiabeticapp.Model.Doctor;
import com.example.doctorsdiabeticapp.Model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class CzatActivity extends BaseActivity {

    RecyclerView recyclerView;
    UserAdapter userAdapter;
    List<User> mUsers;
    FirebaseUser fUser;
    DatabaseReference reference;
    List<String> usersList;
    androidx.appcompat.widget.Toolbar toolbar;
    String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_czat);
        Initialize();
        UserSelectingList();

    }

    public void Initialize() {
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        fUser = FirebaseAuth.getInstance().getCurrentUser();
        usersList = new ArrayList<>();
        reference = FirebaseDatabase.getInstance().getReference("Chat");
        toolbar = findViewById(R.id.toolbar);
        title = getResources().getString(R.string.setting_toolbar);
        setToolbarOther(title);
        mUsers = new ArrayList<>();
    }


    public void UserSelectingList() {

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                usersList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    ChatMessage chatMessage = dataSnapshot.getValue(ChatMessage.class);
                    if (chatMessage.getSender().equals(fUser.getUid())) {
                        usersList.add(chatMessage.getReceiver());
                    }
                    if (chatMessage.getReceiver().equals(fUser.getUid())) {
                        usersList.add(chatMessage.getSender());
                    }

                }
                readChats();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void readChats() {

        reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                 mUsers.clear();
                User user;
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    user = dataSnapshot.getValue(User.class);
                    try {
                        if (user != null) {
                            for (int i = 0; i < usersList.size(); i++) {
                                if (user.getId().equals(usersList.get(i))) {
                                    if (mUsers.size() != 0) {
                                        for (User user1 : mUsers) {
                                            if (!user.getId().equals(user1.getId())) {
                                                mUsers.add(user);
                                            }
                                        }
                                    } else {
                                        mUsers.add(user);
                                    }
                                }
                            }
                        }
                    } catch (Exception e) {
                        Log.e("0",e.toString());
                    }
                }
                userAdapter = new UserAdapter(getApplicationContext(), mUsers);
                recyclerView.setAdapter(userAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                throw error.toException();
            }
        });
    }
}
