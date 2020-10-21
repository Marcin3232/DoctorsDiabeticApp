package com.example.doctorsdiabeticapp.Validation;

import android.widget.EditText;

import java.util.regex.Pattern;

public class VerificationInputText {

    private Pattern emailPattern = Pattern.compile("^[\\w!#$%&’*+/=?`{|}~^-]+" +
            "(?:\\.[\\w!#$%&’*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$");
    private Pattern passwordPattern = Pattern.compile("^(?=.*[0-9])"
            + "(?=.*[a-z])(?=.*[A-Z])"
            + "(?=\\S+$).{8,20}$");

    public boolean validateName(EditText Name, String WarningTextEmpty, String WarningText) {
        String check = Name.getText().toString().trim();

        if (check.isEmpty()) {
            Name.requestFocus();
            Name.setError(WarningTextEmpty);
            return false;
        } else if (!check.matches("[a-zA-Z]+")) {
            Name.requestFocus();
            Name.setError(WarningText);
            return false;
        } else {
            return true;
        }
    }

    public boolean validateEmail(EditText Name, String WarningTextEmpty, String WarningText) {
        String check = Name.getText().toString().trim();
        if (check.isEmpty()) {
            Name.requestFocus();
            Name.setError(WarningTextEmpty);
            return false;
        } else if (check.matches(String.valueOf(emailPattern)) == false) {
            Name.requestFocus();
            Name.setError(WarningText);
            return false;
        } else {
            return true;
        }
    }

    public boolean validatePassword(EditText Name, String WarningTextEmpty, String WarningText) {
        String check = Name.getText().toString().trim();
        if (check.isEmpty()) {
            Name.requestFocus();
            Name.setError(WarningTextEmpty);
            return false;
        } else if (check.matches(String.valueOf(passwordPattern)) == false) {
            Name.requestFocus();
            Name.setError(WarningText);
            return false;
        } else {
            return true;
        }
    }

    public boolean validateCompare(EditText Name, EditText CompareName, String WarningTextEmpty, String WarningText) {
        String check = Name.getText().toString().trim();
        if (check.isEmpty()) {
            Name.requestFocus();
            Name.setError(WarningTextEmpty);
            return false;
        } else if (check == CompareName.getText().toString().trim()) {
            Name.requestFocus();
            Name.setError(WarningText);
            return false;
        } else {
            return true;
        }
    }

}

