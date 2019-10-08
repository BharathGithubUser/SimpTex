package com.belivnat.simptex.modules.chats;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.belivnat.simptex.R;
import com.belivnat.simptex.model.Chats;
import com.belivnat.simptex.utils.Utils;

import java.util.ArrayList;

public class ChatsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<Chats> chats;
    private Context context;
    private String currentSenderName;
    private final int VIEW_TYPE_SENDER = 0;
    private final int VIEW_TYPE_RECEIVER = 1;
    private final int VIEW_TYPE_UNKNOWN = -1;

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
                View senderView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_chat_sender_row, parent, false);
                return new SenderViewHolder(senderView);
            case VIEW_TYPE_RECEIVER:
                View receiverView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_chat_receiver_row, parent, false);
                return new ReceiverViewHolder(receiverView);
            case VIEW_TYPE_UNKNOWN:
                View unknownView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_chat_receiver_row, parent, false);
                return new ReceiverViewHolder(unknownView);
            default:
                View defaultView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_chat_sender_row, parent, false);
                return new SenderViewHolder(defaultView);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof SenderViewHolder) {
            ((SenderViewHolder) holder).tvMessage.setText(chats.get(position).getMessage());
            ((SenderViewHolder) holder).tvSentOn.setText(chats.get(position).getSent_on());
        } else if (holder instanceof ReceiverViewHolder) {
            ((ReceiverViewHolder) holder).tvMessage.setText(chats.get(position).getMessage());
            ((ReceiverViewHolder) holder).tvSentOn.setText(chats.get(position).getSent_on());
        } else {
            Utils.makeToast(context, "Something Went Wrong!");
        }
    }

    @Override
    public int getItemCount() {
        return chats.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (chats != null && chats.get(position).getSender().equals(currentSenderName)) {
            return VIEW_TYPE_RECEIVER;
        } else if (chats != null && !(chats.get(position).getSender().equals(currentSenderName))) {
            return VIEW_TYPE_SENDER;
        } else {
            return VIEW_TYPE_UNKNOWN;
        }
    }

    public class SenderViewHolder extends RecyclerView.ViewHolder {
        TextView tvMessage, tvSentOn;

        public SenderViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMessage = itemView.findViewById(R.id.txt_sender_message);
            tvSentOn = itemView.findViewById(R.id.txt_sent_on);

        }
    }

    public class ReceiverViewHolder extends RecyclerView.ViewHolder {
        TextView tvMessage, tvSentOn;

        public ReceiverViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMessage = itemView.findViewById(R.id.txt_receiver_message);
            tvSentOn = itemView.findViewById(R.id.txt_sent_on);
        }
    }
}
