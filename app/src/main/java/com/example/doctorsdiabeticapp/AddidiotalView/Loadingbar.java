package com.example.doctorsdiabeticapp.AddidiotalView;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;

import com.example.doctorsdiabeticapp.R;

public class Loadingbar {
    Activity activity;
    AlertDialog dialog;

    public Loadingbar(Activity thisactivity) {
        activity = thisactivity;
    }

    public void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.loading_bar, null));
        dialog = builder.create();
        dialog.show();
    }

    public void dismissbar() {
        dialog.dismiss();
    }
}
