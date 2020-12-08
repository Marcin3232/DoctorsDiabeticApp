package com.example.doctorsdiabeticapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.doctorsdiabeticapp.Adapter.VerificationAdapter;
import com.example.doctorsdiabeticapp.BaseActivity.BaseActivity;
import com.example.doctorsdiabeticapp.Model.Doctor;
import com.example.doctorsdiabeticapp.OtherClass.OnItemClickRecyclerListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class VerifyUsersActivity extends BaseActivity implements OnItemClickRecyclerListener {

    private String title;
    private RecyclerView recyclerView;
    private VerificationAdapter adapter;
    private ArrayList<Doctor> doctorArrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_users);

        initialize();
        getDoctors();
    }

    private void initialize() {
        title = getString(R.string.unverification_users_string);
        setToolbarOther(title);
        recyclerView = findViewById(R.id.recycler_view_items);
        doctorArrayList = new ArrayList<>();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


    }

    public void getDoctors() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Doctors");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                doctorArrayList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Doctor doctor = dataSnapshot.getValue(Doctor.class);
                    if (!doctor.getVerification()) {
                        doctorArrayList.add(doctor);
                    }

                }
                adapter = new VerificationAdapter(getApplicationContext(), doctorArrayList, VerifyUsersActivity.this);
                recyclerView.setAdapter(adapter);
                recyclerView.invalidate();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("TAG", "Exception: " + error.toString());
            }
        });
    }

    @Override
    public void onClickRecyclerItem(int position) {

    }
}