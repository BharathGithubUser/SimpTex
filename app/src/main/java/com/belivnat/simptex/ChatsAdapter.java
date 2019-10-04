package com.belivnat.simptex;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ChatsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    ArrayList<Chats> chats;
    Context context;
    String currentSenderName;
    final int VIEW_TYPE_SENDER = 0;
    final int VIEW_TYPE_RECEIVER = 1;
    final int VIEW_TYPE_UNKNOWN = -1;

    public ChatsAdapter(Context context, ArrayList<Chats> chats, String currentSenderName) {
        this.context = context;
        this.chats = chats;
        this.currentSenderName = currentSenderName;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_SENDER:
                View senderView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_chat_sender_row,parent,false);
                return new SenderViewHolder(senderView);
            case VIEW_TYPE_RECEIVER:
                View receiverView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_chat_receiver_row,parent,false);
                return new ReceiverViewHolder(receiverView);
            case VIEW_TYPE_UNKNOWN:
                View unknownView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_chat_receiver_row,parent,false);
                return new ReceiverViewHolder(unknownView);
            default:
                View defaultView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_chat_sender_row,parent,false);
                return new SenderViewHolder(defaultView);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return chats.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (chats != null && chats.get(position).getSender().equals(currentSenderName)) {
            return VIEW_TYPE_SENDER;
        } else if (chats != null && !(chats.get(position).getSender().equals(currentSenderName))) {
            return VIEW_TYPE_RECEIVER;
        } else {
            return VIEW_TYPE_UNKNOWN;
        }
    }

    public class SenderViewHolder extends RecyclerView.ViewHolder {
        public SenderViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public class ReceiverViewHolder extends RecyclerView.ViewHolder {
        public ReceiverViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
