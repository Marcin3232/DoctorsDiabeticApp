package com.example.doctorsdiabeticapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doctorsdiabeticapp.Model.ChatMessage;
import com.example.doctorsdiabeticapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

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
        ChatMessage chatMessage = mChat.get(position);
        holder.show_message.setText(chatMessage.getMessage());
        if (mImageUrl.equals("defaul")) {
            holder.profileImage.setImageResource(R.drawable.example_profile);
        } else {

        }
    }

    @Override
    public int getItemCount() {
        return mChat.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView show_message;
        public ImageView profileImage;

        public ViewHolder(View view) {
            super(view);
            show_message = view.findViewById(R.id.show_message_czat);
            profileImage = view.findViewById(R.id.profile_image_czat);
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