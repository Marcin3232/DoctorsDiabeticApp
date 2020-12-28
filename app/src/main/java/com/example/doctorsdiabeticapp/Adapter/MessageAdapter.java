package com.example.doctorsdiabeticapp.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.doctorsdiabeticapp.Model.ChatMessage;
import com.example.doctorsdiabeticapp.R;
import com.example.doctorsdiabeticapp.Security.SecurityString;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    public static final int MSG_TYPE_LEFT = 0;
    public static final int MSG_TYPE_RIGHT = 1;

    private View view;
    private Context mContext;
    private List<ChatMessage> mChat;
    private String mImageUrl;
    FirebaseUser fuser;

    public MessageAdapter(Context mContext, List<ChatMessage> mChat, String imageUrl) {
        this.mContext = mContext;
        this.mChat = mChat;
        this.mImageUrl = imageUrl;

    }

    @NonNull
    @Override
    public MessageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == MSG_TYPE_RIGHT) {
            view = LayoutInflater.from(mContext).inflate(R.layout.chat_item_sender, parent, false);
            return new MessageAdapter.ViewHolder(view);
        } else {
            view = LayoutInflater.from(mContext).inflate(R.layout.chat_item_receiver, parent, false);
            return new MessageAdapter.ViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.ViewHolder holder, int position) {
        SecurityString securityString=new SecurityString();
        ChatMessage chatMessage = mChat.get(position);

        holder.show_message.setText(securityString.decrypt(chatMessage.getMessage()));

        try {
            if (mImageUrl.equals("")) {
                holder.profileImage.setImageResource(R.drawable.example_profile);
            } else {
                Glide.with(mContext).load(mImageUrl).into(holder.profileImage);
            }
        } catch (NullPointerException e) {
            Log.e("TAG", "Exception: " + e.toString());
        } catch (Exception e) {
            Log.e("TAG", e.toString());

        }
    }

    @Override
    public int getItemCount() {
        return mChat.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView show_message;
        public CircleImageView profileImage;

        public ViewHolder(View view) {
            super(view);
            show_message = view.findViewById(R.id.show_message_czat);
            profileImage = view.findViewById(R.id.profile_image);
        }

    }

    @Override
    public int getItemViewType(int position) {
        fuser = FirebaseAuth.getInstance().getCurrentUser();
        if (mChat.get(position).getSender().equals(fuser.getUid())) {
            return MSG_TYPE_RIGHT;
        } else {
            return MSG_TYPE_LEFT;
        }
    }

}