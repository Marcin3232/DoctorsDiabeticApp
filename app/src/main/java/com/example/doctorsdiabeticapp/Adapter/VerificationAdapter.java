package com.example.doctorsdiabeticapp.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.doctorsdiabeticapp.Model.Doctor;
import com.example.doctorsdiabeticapp.OtherClass.OnItemClickRecyclerListener;
import com.example.doctorsdiabeticapp.R;


import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class VerificationAdapter extends RecyclerView.Adapter<VerificationAdapter.ViewHolder> {

    private View view;

    private Context mContext;
    private OnItemClickRecyclerListener onItemClickRecyclerListener;
    private List<Doctor> mDoctors;

    public VerificationAdapter(Context mContex, List<Doctor> mDoctors,
                               OnItemClickRecyclerListener onItemClickRecyclerListener) {
        this.mContext = mContex;
        this.onItemClickRecyclerListener = onItemClickRecyclerListener;
        this.mDoctors = mDoctors;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(mContext).inflate(R.layout.doctor_item, parent, false);
        return new VerificationAdapter.ViewHolder(view, onItemClickRecyclerListener);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Doctor doctor = mDoctors.get(position);
        holder.username.setText(doctor.getName() + " " + doctor.getSurname());
        holder.title.setText(doctor.getTitle());
        try {
            if (doctor.getUrlImage().equals("")) {
                holder.profileImage.setImageResource(R.drawable.example_profile);
            } else {
                Glide.with(mContext).load(doctor.getUrlImage()).into(holder.profileImage);
            }
        } catch (NullPointerException e) {
            Log.e("TAG", "Exception: " + e.toString());
        }
    }

    @Override
    public int getItemCount() {
        return mDoctors.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView username, title;
        public CircleImageView profileImage;
        public OnItemClickRecyclerListener onItemClickRecyclerListener;

        public ViewHolder(@NonNull View itemView, OnItemClickRecyclerListener onClick) {
            super(itemView);
            username = itemView.findViewById(R.id.user_name);
            title = itemView.findViewById(R.id.title);
            profileImage = itemView.findViewById(R.id.profile_image_czat);
            this.onItemClickRecyclerListener = onClick;
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            onItemClickRecyclerListener.onClickRecyclerItem(getAdapterPosition());
        }
    }
}
