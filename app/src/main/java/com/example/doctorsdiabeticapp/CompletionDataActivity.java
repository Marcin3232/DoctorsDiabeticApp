package com.example.doctorsdiabeticapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.doctorsdiabeticapp.AddidiotalView.Loadingbar;
import com.example.doctorsdiabeticapp.BaseActivity.BaseActivity;
import com.example.doctorsdiabeticapp.FireBase.DoctorCallback;
import com.example.doctorsdiabeticapp.FireBase.ReadDataBaseDoctor;
import com.example.doctorsdiabeticapp.FireBase.WriteDataBaseDoctor;
import com.example.doctorsdiabeticapp.Model.Doctor;
import com.example.doctorsdiabeticapp.Validation.VerificationInputText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;


public class CompletionDataActivity extends BaseActivity {

    EditText Name, Surname, Title, Phone, Describe;
    Spinner Gender;
    FirebaseAuth mAuth;
    DatabaseReference reference;
    Button CommitButton;
    VerificationInputText verificationInputText;
    Doctor doctor;
    Loadingbar loadingbar;
    androidx.appcompat.widget.Toolbar toolbar;
    String title;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completion_data);
        Initialize();
        LoadData();
        CommitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validation() == true) {
                    loadingbar.showDialog();
                    doctor = getDoctor();
                    WriteDataBaseDoctor writeDataBaseDoctor = new WriteDataBaseDoctor(mAuth);
                    writeDataBaseDoctor.UpdateDataBase(doctor, loadingbar);
                    Toast.makeText(getApplicationContext(),
                            getResources().getString(R.string.success_updata),
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(),
                            getResources().getText(R.string.updata_fail),
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void Initialize() {
        Name = findViewById(R.id.edit_Name);
        Surname = findViewById(R.id.edit_Surname);
        Gender = findViewById(R.id.spinner_gender);
        Title = findViewById(R.id.edit_Title);
        Phone = findViewById(R.id.edit_Phone);
        CommitButton = findViewById(R.id.Commit_Change_button);
        Describe = findViewById(R.id.edit_Describe);
        mAuth = FirebaseAuth.getInstance();
        verificationInputText = new VerificationInputText();
        loadingbar = new Loadingbar(this);
        toolbar = findViewById(R.id.toolbar);
        title = getResources().getString(R.string.update_toolbar);
        setToolbarOther(title);
    }

    public void LoadData() {
        ReadDataBaseDoctor dataBaseDoctor = new ReadDataBaseDoctor(mAuth, reference);
        dataBaseDoctor.getData(new DoctorCallback() {
            @Override
            public void onCallback(Doctor value) {
                Name.setText(value.getName());
                Surname.setText(value.getSurname());
                Title.setText(value.getTitle());
                Phone.setText(value.getPhone());
                Describe.setText(value.getDescribe());
            }
        });
    }

    private boolean validation() {
        boolean name = verificationInputText.validateName(Name,
                getResources().getString(R.string.write_name),
                getResources().getString(R.string.write_name_warning));
        boolean surname = verificationInputText.validateName(Surname,
                getResources().getString(R.string.write_surname),
                getResources().getString(R.string.write_surname_warning));
        boolean title = verificationInputText.validateName(Title,
                getResources().getString(R.string.write_title),
                getResources().getString(R.string.write_title_waring));
        boolean phone = verificationInputText.validatePhoneNumber(Phone,
                getResources().getString(R.string.write_phone),
                getResources().getString(R.string.write_phone_warning));
        boolean describe = verificationInputText.validateDescription(Describe,
                getResources().getString(R.string.write_describe),
                getResources().getString(R.string.write_describe_warning));
        if ((name && surname && title && phone && describe) == false) {
            Toast.makeText(CompletionDataActivity.this,
                    getResources().getString(R.string.correct_need_all),
                    Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return name && surname && title && phone && describe;
        }
    }

    public Doctor getDoctor() {
        doctor = new Doctor(
                Name.getText().toString(),
                Surname.getText().toString(),
                Title.getText().toString(),
                false,
                Describe.getText().toString(),
                Phone.getText().toString(),
                mAuth.getUid(),
                Gender.getSelectedItem().toString());
        return doctor;
    }

}
