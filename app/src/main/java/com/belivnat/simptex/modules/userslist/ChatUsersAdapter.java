package com.belivnat.simptex.modules.userslist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.belivnat.simptex.R;
import com.belivnat.simptex.model.Users;
import com.bumptech.glide.Glide;

import java.util.List;

public class ChatUsersAdapter extends RecyclerView.Adapter<ChatUsersAdapter.MyViewHolder> {
    List<Users> chatUsers;
    Context context;
    public ChatUsersAdapter(List<Users> chatUsers, Context context) {
        this.chatUsers = chatUsers;
        this.context = context;
    }
    @NonNull
    @Override
    public ChatUsersAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_chat_list_row, parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatUsersAdapter.MyViewHolder holder, int position) {
        if (chatUsers.get(position) != null) {
            holder.tvChatUser.setText(chatUsers.get(position).userName);
            holder.tvRecentChat.setText(chatUsers.get(position).recentChat);
            String userImageUrl = chatUsers.get(position).getUserImageUrl();
            if (userImageUrl != null && !userImageUrl.equals("")) {
                Glide.with(context).load(chatUsers.get(position).getUserImageUrl()).placeholder(R.drawable.ic_user_placeholder).into(holder.imgChatUser);
            } else {
                holder.imgChatUser.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_user_placeholder));
            }
        }
    }

    @Override
    public int getItemCount() {
        return chatUsers.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvChatUser, tvRecentChat;
        ImageView imgChatUser;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvChatUser = itemView.findViewById(R.id.tv_user);
            tvRecentChat = itemView.findViewById(R.id.tv_recent_chat);
            imgChatUser = itemView.findViewById(R.id.img_chat_user);
        }
    }
}
