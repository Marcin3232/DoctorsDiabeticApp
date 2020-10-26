package com.example.doctorsdiabeticapp.FireBase;

import androidx.annotation.NonNull;

import com.example.doctorsdiabeticapp.AddidiotalView.Loadingbar;
import com.example.doctorsdiabeticapp.Model.Doctor;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class WriteDataBaseDoctor {

    FirebaseAuth mAht;

    public WriteDataBaseDoctor() {
    }

    public WriteDataBaseDoctor(FirebaseAuth mAht) {
        this.mAht = mAht;
    }

    public void UpdateDataBase(Doctor doctor, Loadingbar loadingbar){
        FirebaseDatabase.getInstance().getReference("Doctors").child(mAht.getUid()).
                setValue(doctor);
        loadingbar.dismissbar();
    }
}
