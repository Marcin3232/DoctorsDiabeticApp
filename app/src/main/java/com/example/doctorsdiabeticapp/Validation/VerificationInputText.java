package com.example.doctorsdiabeticapp.Validation;

import android.widget.EditText;
import android.widget.Spinner;

import java.util.regex.Pattern;

public class VerificationInputText {

    private Pattern emailPattern = Pattern.compile("^[\\w!#$%&’*+/=?`{|}~^-]+" +
            "(?:\\.[\\w!#$%&’*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$");
    private Pattern passwordPattern = Pattern.compile("^(?=.*[0-9])"
            + "(?=.*[a-z])(?=.*[A-Z])"
            + "(?=\\S+$).{8,20}$");
    private Pattern phoneNumberPattern = Pattern.compile("\\d{3}[-\\.\\s]\\d{3}[-\\.\\s]\\d{3}$");
    private Pattern phoneNumberPattern1 = Pattern.compile("\\d{9}");
    private Pattern phoneNumberPattern2 = Pattern.compile("\\(\\d{3}\\)-\\d{3}-\\d{3}");
    private Pattern phoneNumberPattern3 = Pattern.compile("\\d{11}");
    private Pattern phoneNumberPattern4 = Pattern.compile("\\(\\d{2}\\)-\\d{3}-\\d{3}-\\d{3}");
    private Pattern phoneNumberPattern5 = Pattern.compile("\\d{2}[-\\.\\s]\\d{3}[-\\.\\s]\\d{3}" +
            "[-\\.\\s]\\d{3}$");

    private Pattern describePattern = Pattern.compile("^.{60,}$");

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

    public boolean validatePhoneNumber(EditText Name, String WarningTextEmpty, String WarningText) {
        String check = Name.getText().toString().trim();
        if (check.isEmpty()) {
            Name.requestFocus();
            Name.setError(WarningTextEmpty);
            return false;
        } else if (check.matches(String.valueOf(phoneNumberPattern)) ||
                check.matches(String.valueOf(phoneNumberPattern1)) ||
                check.matches(String.valueOf(phoneNumberPattern2)) ||
                check.matches(String.valueOf(phoneNumberPattern3)) ||
                check.matches(String.valueOf(phoneNumberPattern4)) ||
                check.matches(String.valueOf(phoneNumberPattern5)) == true) {
            return true;
        } else {
            Name.requestFocus();
            Name.setError(WarningText);
            return false;
        }
    }

    public boolean validateDescription(EditText Name, String WarningTextEmpty, String WarningText) {
        String check = Name.getText().toString().trim();
        if (check.isEmpty()) {
            Name.requestFocus();
            Name.setError(WarningTextEmpty);
            return false;
        } else if (check.matches(String.valueOf(describePattern)) == false) {
            Name.requestFocus();
            Name.setError(WarningText);
            return false;
        } else {
            return true;
        }
    }


}

