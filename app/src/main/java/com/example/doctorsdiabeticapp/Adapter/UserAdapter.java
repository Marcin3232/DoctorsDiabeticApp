package com.example.doctorsdiabeticapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.doctorsdiabeticapp.CzatMessageActivity;
import com.example.doctorsdiabeticapp.Model.User;
import com.example.doctorsdiabeticapp.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
    private View view;
    private Context mContext;
    private List<User> mUsers;

    public UserAdapter(Context mContext, List<User> mUsers) {
        this.mContext = mContext;
        this.mUsers = mUsers;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(mContext).inflate(R.layout.user_item, parent, false);
        return new UserAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final User user = mUsers.get(position);
        holder.username.setText(user.getName());

        if(user.getUrlImage().equals("")){
            holder.profileImage.setImageResource(R.drawable.example_profile);
        }
        else {
            Glide.with(mContext).load(user.getUrlImage()).into(holder.profileImage);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext, CzatMessageActivity.class);
                intent.putExtra("userId", user.getId());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView username;
        public CircleImageView profileImage;

        public ViewHolder(View view) {
            super(view);
            username = view.findViewById(R.id.user_name);
            profileImage = view.findViewById(R.id.profile_image_czat);
        }

    }
}
